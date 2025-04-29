import { DataSource, ViewColumn, ViewEntity } from "typeorm";

import {
  ProductEntity,
  ProductPriceEntity,
  ProductImageEntity,
  BrandEntity,
  SellerEntity,
  ReviewEntity,
} from "../entities";

@ViewEntity({
  expression: (dataSource: DataSource) => {
    return dataSource
      .getRepository(ProductEntity)
      .createQueryBuilder("products")
      .leftJoin(SellerEntity, "sellers", "sellers.id = products.seller_id")
      .leftJoin(BrandEntity, "brands", "brands.id = products.brand_id")
      .innerJoin(ProductPriceEntity, "product_prices", "product_prices.product_id = products.id")
      .leftJoin(ProductImageEntity, "product_images", "product_images.product_id = products.id")
      .leftJoin(ReviewEntity, "reviews", "reviews.product_id = products.id")
      .groupBy("products.id")
      .addGroupBy("product_prices.base_price")
      .addGroupBy("product_prices.sale_price")
      .addGroupBy("product_prices.currency")
      .addGroupBy("product_prices.tax_rate")
      .addGroupBy("product_images.id")
      .addGroupBy("product_images.url")
      .addGroupBy("product_images.alt_text")
      .addGroupBy("brands.id")
      .addGroupBy("sellers.id")
      .select([
        "products.id as id",
        "products.name as name",
        "products.slug as slug",
        "products.short_description as short_description",
        "products.full_description as full_description",

        "sellers.id",
        "sellers.name",
        "sellers.description",
        "sellers.logo_url",
        "sellers.rating",
        "sellers.contact_email",
        "sellers.contact_phone",

        "brands.id",
        "brands.name",
        "brands.description",
        "brands.logo_url",
        "brands.website",

        "products.status as status",
        "products.created_at as created_at",
        "products.updated_at as updated_at",

        "product_prices.base_price",
        "product_prices.sale_price",
        "product_prices.currency",
        "product_prices.tax_rate",

        "product_images.id",
        "product_images.url",
        "product_images.alt_text",
        "product_images.is_primary",
        "product_images.display_order",
        "product_images.option.id",
      ])
      .addSelect(
        "FLOOR(((base_price - sale_price) * 100.0) / base_price)",
        "product_prices_discount_percentage",
      )
      .addSelect("ROUND(AVG(reviews.rating), 1)", "rating_average")
      .addSelect("COUNT(reviews.id)", "review_count")
      .addSelect(`COUNT(CASE WHEN reviews.rating = 1 THEN 1 END)`, "rating_1")
      .addSelect(`COUNT(CASE WHEN reviews.rating = 2 THEN 1 END)`, "rating_2")
      .addSelect(`COUNT(CASE WHEN reviews.rating = 3 THEN 1 END)`, "rating_3")
      .addSelect(`COUNT(CASE WHEN reviews.rating = 4 THEN 1 END)`, "rating_4")
      .addSelect(`COUNT(CASE WHEN reviews.rating = 5 THEN 1 END)`, "rating_5");
  },
})
export class ProductDetailView {
  @ViewColumn() id: number;
  @ViewColumn() name: string;
  @ViewColumn() slug: string;
  @ViewColumn() short_description: string;
  @ViewColumn() full_description: string;

  @ViewColumn() sellers_id: number;
  @ViewColumn() sellers_name: string;
  @ViewColumn() sellers_description: string;
  @ViewColumn() sellers_logo_url: string;
  @ViewColumn() sellers_rating: number;
  @ViewColumn() sellers_contact_email: string;
  @ViewColumn() sellers_contact_phone: string;

  @ViewColumn() brands_id: number;
  @ViewColumn() brands_name: string;
  @ViewColumn() brands_description: string;
  @ViewColumn() brands_logo_url: string;
  @ViewColumn() brands_website: string;

  @ViewColumn() status: string;
  @ViewColumn() created_at: Date;
  @ViewColumn() updated_at: Date;

  @ViewColumn() product_prices_base_price: number;
  @ViewColumn() product_prices_sale_price: number;
  @ViewColumn() product_prices_currency: string;
  @ViewColumn() product_prices_tax_rate: number;
  @ViewColumn() product_prices_discount_percentage: number;

  @ViewColumn() product_images_id: number;
  @ViewColumn() product_images_url: string;
  @ViewColumn() product_images_alt_text: string;
  @ViewColumn() product_images_is_primary: boolean;
  @ViewColumn() product_images_display_order: number;
  @ViewColumn() product_images_option_id: number;

  @ViewColumn() rating_average: number;
  @ViewColumn() review_count: number;
  @ViewColumn() rating_1: number;
  @ViewColumn() rating_2: number;
  @ViewColumn() rating_3: number;
  @ViewColumn() rating_4: number;
  @ViewColumn() rating_5: number;

  get seller() {
    return {
      id: this.sellers_id,
      name: this.sellers_name,
      description: this.sellers_description,
      logo_url: this.sellers_logo_url,
      rating: this.sellers_rating,
      contact_email: this.sellers_contact_email,
      contact_phone: this.sellers_contact_phone,
    };
  }
  get brand() {
    return {
      id: this.brands_id,
      name: this.brands_name,
      description: this.brands_description,
      logo_url: this.brands_logo_url,
      website: this.brands_website,
    };
  }
  get price() {
    return {
      base_price: this.product_prices_base_price,
      sale_price: this.product_prices_sale_price,
      currency: this.product_prices_currency,
      tax_rate: this.product_prices_tax_rate,
      discount_percentage: this.product_prices_discount_percentage,
    };
  }
  get images() {
    return {
      url: this.product_images_url,
      alt_text: this.product_images_alt_text,
      is_primary: this.product_images_is_primary,
      display_order: this.product_images_display_order,
      option_id: this.product_images_option_id,
    };
  }

  get rating() {
    return {
      average: this.rating_average,
      count: this.review_count,
      distribution: {
        1: this.rating_1,
        2: this.rating_2,
        3: this.rating_3,
        4: this.rating_4,
        5: this.rating_5,
      },
    };
  }
}
