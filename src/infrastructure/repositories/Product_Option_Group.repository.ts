import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Option_Group } from "src/domain/entities";
import { ProductOptionEntity, ProductOptionGroupEntity } from "../entities";

@Injectable()
export default class ProductOptionGroupRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save(option_groups: Product_Option_Group[], product_id: number) {
    for (const { options, ...group_entity } of option_groups) {
      // 상품 옵션 그룹 등록
      const option_group_entity = await this.entity_manager.save(ProductOptionGroupEntity, {
        ...group_entity,
        product: { id: product_id },
      });

      // 상품 옵션 그룹에 속한 옵션 등록
      await this.entity_manager.save(
        ProductOptionEntity,
        options.map((option) => ({ ...option, option_group: option_group_entity })),
      );
    }
  }
}
