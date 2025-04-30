import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Category } from "src/domain/entities";
import { ProductCategoryEntity } from "../entities";

@Injectable()
export default class ProductCategoryRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save(categories: Product_Category[], product_id: number) {
    return await this.entity_manager.save(
      ProductCategoryEntity,
      categories.map(({ category_id, is_primary }) => ({
        product: { id: product_id },
        category: { id: category_id },
        is_primary,
      })),
    );
  }

  async update(categories: Product_Category[], product_id: number) {
    for (const { category_id, is_primary } of categories) {
      await this.entity_manager.update(
        ProductCategoryEntity,
        { product: { id: product_id } },
        { is_primary, category: { id: category_id } },
      );
    }
  }
}
