import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { CategoryEntity, ProductCategoryEntity, ProductEntity } from "src/infrastructure/entities";
import { ProductSummaryView } from "src/infrastructure/views";

@Injectable()
export default class MainRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async get_new_products(page: number, per_page: number) {
    const products = await this.entity_manager.find(ProductSummaryView, {
      order: { created_at: "DESC" },
      skip: (page - 1) * per_page,
      take: per_page,
    });

    return products;
  }

  async get_popular_products() {
    const query = this.entity_manager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .orderBy("rating", "DESC")
      .limit(5);

    return await query.getMany();
  }

  async get_featured_categories() {
    const query = this.entity_manager
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
