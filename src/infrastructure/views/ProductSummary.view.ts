import { ViewEntity, ViewColumn, DataSource } from "typeorm";

import {
  ProductEntity,
  ProductPriceEntity,
  ProductCategoryEntity,
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
      .leftJoin(
        ProductCategoryEntity,
        "product_categories",
        "product_categories.product_id = products.id",
      )
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
        "product_images.url as image_url",
        "product_images.alt_text as image_alt_text",
        "brands.id as brand_id",
        "brands.name as brand_name",
        "sellers.id as seller_id",
        "sellers.name as seller_name",
        "products.status as status",
        "products.created_at as created_at",
        "product_categories.id as category_id",
      ])
      .addSelect("stock_summary.in_stock", "in_stock")
      .addSelect("ROUND(AVG(reviews.rating), 1)", "rating")
      .addSelect("COUNT(reviews.id)", "review_count")
      .groupBy("products.id")
      .addGroupBy("products.created_at")
      .addGroupBy("product_prices.base_price")
      .addGroupBy("product_prices.sale_price")
      .addGroupBy("product_prices.currency")
      .addGroupBy("product_images.url")
      .addGroupBy("product_images.alt_text")
      .addGroupBy("product_categories.id")
      .addGroupBy("stock_summary.in_stock")
      .addGroupBy("brands.id")
      .addGroupBy("brands.name")
      .addGroupBy("sellers.id")
      .addGroupBy("sellers.name");
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
  image_url: string;

  @ViewColumn()
  image_alt_text: string;

  @ViewColumn()
  brand_id: number;

  @ViewColumn()
  brand_name: string;

  @ViewColumn()
  seller_id: number;

  @ViewColumn()
  seller_name: string;

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

  @ViewColumn()
  category_id: number;
}
