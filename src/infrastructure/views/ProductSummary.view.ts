import { ViewEntity, ViewColumn, DataSource } from "typeorm";

import {
  ProductEntity,
  ProductPriceEntity,
  ProductImageEntity,
  ReviewEntity,
  BrandEntity,
  SellerEntity,
} from "../entities";
import { ProductStockView } from "./ProductStock.view";

@ViewEntity({
  expression: (dataSource: DataSource) => {
    return dataSource
      .getRepository(ProductEntity)
      .createQueryBuilder("products")
      .innerJoin(ProductPriceEntity, "product_prices", "product_prices.product_id = products.id")
      .leftJoin(ProductImageEntity, "product_images", "product_images.product_id = products.id")
      .leftJoin(ReviewEntity, "reviews", "reviews.product_id = products.id")
      .leftJoin(BrandEntity, "brands", "brands.id = products.brand_id")
      .leftJoin(SellerEntity, "sellers", "sellers.id = products.seller_id")
      .leftJoin(ProductStockView, "stock_summary", "stock_summary.product_id = products.id")
      .select([
        "products.id as id",
        "products.name as name",
        "products.slug as slug",
        "products.short_description as short_description",
        "ROUND(product_prices.base_price) as base_price",
        "ROUND(product_prices.sale_price) as sale_price",
        "product_prices.currency as currency",
        "product_images.url",
        "product_images.alt_text",
        "brands.id",
        "brands.name",
        "sellers.id",
        "sellers.name",
        "products.status as status",
        "products.created_at as created_at",
      ])
      .addSelect("stock_summary.in_stock", "in_stock")
      .addSelect("ROUND(AVG(reviews.rating), 1)", "rating")
      .addSelect("COUNT(reviews.id)", "review_count")
      .groupBy("products.id")
      .addGroupBy("product_prices.base_price")
      .addGroupBy("product_prices.sale_price")
      .addGroupBy("product_prices.currency")
      .addGroupBy("product_images.url")
      .addGroupBy("product_images.alt_text")
      .addGroupBy("stock_summary.in_stock")
      .addGroupBy("brands.id")
      .addGroupBy("sellers.id");
  },
})
export class ProductSummaryView {
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
  product_images_url: string;

  @ViewColumn()
  product_images_alt_text: string;

  @ViewColumn()
  brands_id: number;

  @ViewColumn()
  brands_name: string;

  @ViewColumn()
  sellers_id: number;

  @ViewColumn()
  sellers_name: string;

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

  get primary_image() {
    return {
      url: this.product_images_url,
      alt_text: this.product_images_alt_text,
    };
  }

  get brand() {
    return {
      id: this.brands_id,
      name: this.brands_name,
    };
  }

  get seller() {
    return {
      id: this.sellers_id,
      name: this.sellers_name,
    };
  }
}
