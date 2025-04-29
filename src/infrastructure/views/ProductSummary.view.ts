import { ViewEntity, ViewColumn, DataSource } from "typeorm";

import {
  ProductEntity,
  ProductPriceEntity,
  ProductImageEntity,
  ReviewEntity,
  BrandEntity,
  SellerEntity,
  ProductOptionGroupEntity,
  ProductOptionEntity,
} from "../entities";

@ViewEntity({
  expression: (dataSource: DataSource) => {
    return dataSource
      .getRepository(ProductEntity)
      .createQueryBuilder("products")
      .innerJoin(ProductPriceEntity, "product_prices", "product_prices.product_id = products.id")
      .leftJoin(ReviewEntity, "reviews", "reviews.product_id = products.id")
      .leftJoin(BrandEntity, "brands", "brands.id = products.brand_id")
      .leftJoin(SellerEntity, "sellers", "sellers.id = products.seller_id")
      .select([
        "products.id as id",
        "products.name as name",
        "products.slug as slug",
        "products.short_description as short_description",
        "ROUND(product_prices.base_price) as base_price",
        "ROUND(product_prices.sale_price) as sale_price",
        "product_prices.currency as currency",
        "jsonb_build_object('id', brands.id, 'name', brands.name ) as brand",
        "jsonb_build_object('id', sellers.id, 'name', sellers.name ) as seller",
        "products.status as status",
        "products.created_at as created_at",
      ])
      .addSelect("ROUND(AVG(reviews.rating), 1)", "rating")
      .addSelect("COUNT(reviews.id)", "review_count")
      .addSelect((subQuery) => {
        return subQuery
          .select(
            `jsonb_build_object(
              'url', product_images.url,
              'alt_text', product_images.alt_text
              )`,
            "primary_image",
          )
          .from(ProductImageEntity, "product_images")
          .where("product_images.product_id = products.id")
          .orderBy("product_images.is_primary", "DESC")
          .limit(1);
      }, "primary_image")
      .addSelect((subQuery) => {
        return subQuery
          .select("CASE WHEN SUM(product_options.stock) > 0 THEN true ELSE false END", "in_stock")
          .from(ProductOptionGroupEntity, "product_option_groups")
          .leftJoin(
            ProductOptionEntity,
            "product_options",
            "product_options.option_group_id = product_option_groups.id",
          )
          .where("product_option_groups.product_id = products.id")
          .groupBy("product_option_groups.product_id");
      }, "in_stock")
      .groupBy("products.id")
      .addGroupBy("product_prices.base_price")
      .addGroupBy("product_prices.sale_price")
      .addGroupBy("product_prices.currency")
      .addGroupBy("brands.id")
      .addGroupBy("sellers.id");
  },
})
export default class ProductSummaryView {
  @ViewColumn()
  id: number;

  @ViewColumn()
  name: string;

  @ViewColumn()
  slug: string;

  @ViewColumn()
  short_description: string;

  @ViewColumn()
  base_price: number;

  @ViewColumn()
  sale_price: number;

  @ViewColumn()
  currency: string;

  @ViewColumn()
  primary_image: {
    url: string;
    alt_text: string;
  };

  @ViewColumn()
  brand: {
    id: number;
    name: string;
  };

  @ViewColumn()
  seller: {
    id: number;
    name: string;
  };

  @ViewColumn()
  status: string;

  @ViewColumn()
  created_at: Date;

  @ViewColumn()
  in_stock: boolean;

  @ViewColumn()
  rating: number;

  @ViewColumn()
  review_count: number;
}
