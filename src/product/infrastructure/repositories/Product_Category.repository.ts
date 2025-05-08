import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { BaseRepository } from "@libs/domain/repositories";
import { ProductCategoryDTO } from "@product/application/dto";
import { ProductCategoryEntity } from "../entities";

@Injectable()
export default class ProductCategoryRepository extends BaseRepository<ProductCategoryDTO> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async saves(categories: ProductCategoryDTO[]) {
    return await this.entity_manager.save(
      ProductCategoryEntity,
      categories.map(({ category_id, product_id, is_primary }) => ({
        product: { id: product_id },
        category: { id: category_id },
        is_primary,
      })),
    );
  }

  async update({ product_id, category_id, is_primary }: ProductCategoryDTO) {
    const { affected } = await this.entity_manager.update(
      ProductCategoryEntity,
      { product: { id: product_id } },
      { is_primary, category: { id: category_id } },
    );
    return !!affected;
  }
}
