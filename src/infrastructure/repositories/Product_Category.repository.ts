import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Category } from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ProductCategoryEntity, ProductEntity } from "../entities";

@Injectable()
export default class ProductCategoryRepository
  implements IRepository<Product_Category, ProductCategoryEntity>
{
  constructor(private readonly entity_manager: EntityManager) {}

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

  save(param: Product_Category): Promise<ProductCategoryEntity> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<ProductCategoryEntity | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<ProductCategoryEntity[]> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
