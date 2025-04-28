import { EntityManager } from "typeorm";
import { Injectable } from "@nestjs/common";

import {
  ProductEntity,
  ProductPriceEntity,
  ProductCategoryEntity,
  CategoryEntity,
  ReviewEntity,
  BrandEntity,
  SellerEntity,
} from "src/infrastructure/entities";

@Injectable()
export default class MainService {
  constructor(private readonly entityManager: EntityManager) {}

  async getNewProducts() {
    const query = { page: 1, perPage: 5 };

    const products = await this.entityManager.find(ProductEntity, {
      order: { created_at: "DESC" },
      skip: (query.page - 1) * query.perPage,
      take: query.perPage,
    });

    return products;
  }

  async getPopularProducts() {
    const query = this.entityManager
      .getRepository(ProductEntity)
      .createQueryBuilder("products")
      .innerJoinAndSelect(
        ProductPriceEntity,
        "product_prices",
        "product_prices.product_id = products.id",
      )
      .leftJoin(ReviewEntity, "reviews", "reviews.product_id = products.id")
      .leftJoin(BrandEntity, "brands", "brands.id = products.brand_id")
      .leftJoin(SellerEntity, "sellers", "sellers.id = products.seller_id")
      .select([
        "products.id as id",
        "products.name as name",
        "products.slug as slug",
        "products.short_description as short_description",
        "product_prices.base_price as base_price",
        "product_prices.sale_price as sale_price",
        "product_prices.currency as currency",
        "brands.id as brand_id",
        "brands.name as brand_name",
        "sellers.id as sellers_id",
        "sellers.name as seller_name",
        "products.status as status",
        "products.created_at as created_at",
      ])
      .addSelect("ROUND(AVG(reviews.rating), 1)", "rating")
      .addSelect("COUNT(reviews.id)", "review_count")
      .groupBy("products.id")
      .addGroupBy("product_prices.base_price")
      .addGroupBy("product_prices.sale_price")
      .addGroupBy("product_prices.currency")
      .addGroupBy("brands.id")
      .addGroupBy("brands.name")
      .addGroupBy("sellers.id")
      .addGroupBy("sellers.name")
      .orderBy("rating", "DESC")
      .limit(5);

    return await query.getRawMany();
  }

  async getFeaturedCategories() {
    const query = this.entityManager
      .getRepository(CategoryEntity)
      .createQueryBuilder("categories")
      .innerJoinAndSelect(
        ProductCategoryEntity,
        "product_categories",
        "product_categories.category_id = categories.id",
      )
      .innerJoinAndSelect(ProductEntity, "products", "products.id = product_categories.product_id")
      .select([
        "categories.id as id",
        "categories.name as name",
        "categories.slug as slug",
        "categories.image_url as image_url",
      ])
      .addSelect("COUNT(products.id)", "product_count")
      .groupBy("categories.id")
      .orderBy("product_count", "DESC")
      .limit(5);

    return query.getRawMany();
  }
}
