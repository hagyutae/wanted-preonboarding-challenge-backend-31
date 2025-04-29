import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { CategoryEntity, ProductCategoryEntity, ProductEntity } from "src/infrastructure/entities";
import { ProductSummaryView } from "src/infrastructure/views/ProductSummary.view";

@Injectable()
export default class MainService {
  constructor(private readonly entityManager: EntityManager) {}

  async getNewProducts() {
    const page = 1;
    const per_page = 5;

    const products = await this.entityManager.find(ProductEntity, {
      order: { created_at: "DESC" },
      skip: (page - 1) * per_page,
      take: per_page,
    });

    return products;
  }

  async getPopularProducts() {
    const query = this.entityManager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .orderBy("rating", "DESC")
      .limit(5);

    return await query.getMany();
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
