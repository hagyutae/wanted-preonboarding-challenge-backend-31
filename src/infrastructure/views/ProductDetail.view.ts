import { DataSource, ViewColumn, ViewEntity } from "typeorm";

import {
  ProductEntity,
  SellerEntity,
  BrandEntity,
  ProductDetailEntity,
  ProductPriceEntity,
  ProductCategoryEntity,
  CategoryEntity,
  ProductOptionGroupEntity,
  ProductOptionEntity,
  ProductImageEntity,
  ProductTagEntity,
  TagEntity,
  ReviewEntity,
} from "../entities";

@ViewEntity({
  expression: (dataSource: DataSource) => {
    return dataSource
      .getRepository(ProductEntity)
      .createQueryBuilder("products")
      .leftJoin(SellerEntity, "sellers", "sellers.id = products.seller_id")
      .leftJoin(BrandEntity, "brands", "brands.id = products.brand_id")
      .innerJoin(ProductDetailEntity, "product_details", "product_details.product_id = products.id")
      .leftJoin(ProductImageEntity, "product_images", "product_images.product_id = products.id")
      .leftJoin(ProductTagEntity, "product_tag", "product_tag.product_id = products.id")
      .leftJoin(TagEntity, "tags", "tags.id = product_tag.tag_id")
      .select([
        "products.id as id",
        "products.name as name",
        "products.slug as slug",
        "products.short_description as short_description",
        "products.full_description as full_description",

        "to_jsonb(sellers) - 'created_at' AS seller",
        "to_jsonb(brands) - 'slug' AS brand",

        "products.status as status",
        "products.created_at as created_at",
        "products.updated_at as updated_at",

        "to_jsonb(product_details) - 'product_id' - 'id' AS detail",
        "array_agg(to_jsonb(product_images) - 'product_id') AS images",
        "array_agg(to_jsonb(tags)) AS tags",
      ])
      .addSelect((subQuery) => {
        return subQuery
          .select(
            `jsonb_build_object(
              'base_price', price.base_price,
              'sale_price', price.sale_price,
              'currency', price.currency,
              'tax_rate', price.tax_rate,
              'discount_percentage', FLOOR(((price.base_price - price.sale_price) * 100.0) / price.base_price)
              )`,
            "price",
          )
          .from(ProductPriceEntity, "price")
          .where("price.product_id = products.id");
      }, "price")
      .addSelect((subQuery) => {
        return subQuery
          .select(
            `array_agg(jsonb_build_object(
              'id', categories.id,
              'name', categories.name,
              'slug', categories.slug,
              'is_primary', product_categories.is_primary,
              'parent', jsonb_build_object(
                'id', parent.id,
                'name', parent.name,
                'slug', parent.slug
              ))
            )`,
            "categories",
          )
          .from(ProductCategoryEntity, "product_categories")
          .innerJoin(CategoryEntity, "categories", "categories.id = product_categories.category_id")
          .leftJoin(CategoryEntity, "parent", "parent.id = categories.parent_id")
          .where("product_categories.product_id = products.id")
          .groupBy("product_categories.product_id");
      }, "categories")
      .addSelect((subQuery) => {
        return subQuery
          .select(
            `array_agg(jsonb_build_object(
              'id', product_option_groups.id,
              'name', product_option_groups.name,
              'display_order', product_option_groups.display_order,
              'options', (
                SELECT jsonb_agg(to_jsonb(product_options) - 'option_group_id')
                FROM product_options product_options
                WHERE product_options.option_group_id = product_option_groups.id
              ))
            )`,
            "option_groups",
          )
          .from(ProductOptionGroupEntity, "product_option_groups")
          .leftJoin(
            ProductOptionEntity,
            "product_options",
            "product_options.option_group_id = product_option_groups.id",
          )
          .where("product_option_groups.product_id = products.id")
          .groupBy("product_option_groups.id")
          .addGroupBy("product_option_groups.name")
          .addGroupBy("product_option_groups.display_order");
      }, "option_groups")
      .addSelect((subQuery) => {
        return subQuery
          .select(
            `jsonb_build_object(
              'average', ROUND(AVG(reviews.rating), 1),
              'count', COUNT(reviews.id),
              'distribution', jsonb_build_object(
                '5', COUNT(CASE WHEN reviews.rating = 5 THEN 1 END),
                '4', COUNT(CASE WHEN reviews.rating = 4 THEN 1 END),
                '3', COUNT(CASE WHEN reviews.rating = 3 THEN 1 END),
                '2', COUNT(CASE WHEN reviews.rating = 2 THEN 1 END),
                '1', COUNT(CASE WHEN reviews.rating = 1 THEN 1 END)
                )
              )`,
            "rating",
          )
          .from(ReviewEntity, "reviews")
          .where("reviews.product_id = products.id");
      }, "rating")
      .groupBy("products.id")
      .addGroupBy("sellers")
      .addGroupBy("brands")
      .addGroupBy("product_details");
  },
})
export default class ProductDetailView {
  @ViewColumn() id: number;
  @ViewColumn() name: string;
  @ViewColumn() slug: string;
  @ViewColumn() short_description: string;
  @ViewColumn() full_description: string;
  @ViewColumn() status: string;
  @ViewColumn() created_at: Date;
  @ViewColumn() updated_at: Date;

  @ViewColumn() seller: SellerEntity;

  @ViewColumn() brand: BrandEntity;

  @ViewColumn() detail: ProductDetailEntity;

  @ViewColumn() price: ProductPriceEntity & { discount_percentage: number };

  @ViewColumn() categories: ProductCategoryEntity[];

  @ViewColumn() option_groups: ProductOptionGroupEntity[];

  @ViewColumn() images: ProductImageEntity[];

  @ViewColumn() tags: TagEntity[];

  @ViewColumn() rating: {
    average: number;
    count: number;
    distribution: {
      "5": number;
      "4": number;
      "3": number;
      "2": number;
      "1": number;
    };
  };
}
