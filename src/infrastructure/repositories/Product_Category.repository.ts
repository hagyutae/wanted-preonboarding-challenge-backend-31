import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Category } from "src/domain/entities";
import { ProductCategoryEntity } from "../entities";
import BaseRepository from "./BaseRepository";

@Injectable()
export default class ProductCategoryRepository extends BaseRepository<
  Product_Category,
  ProductCategoryEntity
> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async saves(categories: Product_Category[]) {
    return await this.entity_manager.save(
      ProductCategoryEntity,
      categories.map(({ category_id, product_id, is_primary }) => ({
        product: { id: product_id },
        category: { id: category_id },
        is_primary,
      })),
    );
  }

  async update({ product_id, category_id, is_primary }: Product_Category) {
    await this.entity_manager.update(
      ProductCategoryEntity,
      { product: { id: product_id } },
      { is_primary, category: { id: category_id } },
    );
  }
}
