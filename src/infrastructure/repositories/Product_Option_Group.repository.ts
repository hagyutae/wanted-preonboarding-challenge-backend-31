import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Option_Group } from "src/domain/entities";
import { ProductOptionEntity, ProductOptionGroupEntity } from "../entities";
import BaseRepository from "./BaseRepository";

@Injectable()
export default class ProductOptionGroupRepository extends BaseRepository<
  Product_Option_Group,
  ProductOptionGroupEntity
> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async saves(option_groups: Product_Option_Group[]): Promise<ProductOptionGroupEntity[]> {
    const option_group_entities: ProductOptionGroupEntity[] = [];

    for (const { options, product_id, ...group_entity } of option_groups) {
      // 상품 옵션 그룹 등록
      const option_group_entity = await this.entity_manager.save(ProductOptionGroupEntity, {
        ...group_entity,
        product: { id: product_id },
      });
      option_group_entities.push(option_group_entity);

      // 상품 옵션 그룹에 속한 옵션 등록
      await this.entity_manager.save(
        ProductOptionEntity,
        options.map((option) => ({ ...option, option_group: option_group_entity })),
      );
    }
    return option_group_entities;
  }
}
