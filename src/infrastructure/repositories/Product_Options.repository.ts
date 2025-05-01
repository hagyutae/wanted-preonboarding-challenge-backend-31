import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Option } from "src/domain/entities";
import { ProductOptionEntity } from "../entities";
import BaseRepository from "./BaseRepository";

@Injectable()
export default class ProductOptionsRepository extends BaseRepository<Product_Option> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async save({ option_group_id, ...options }: Product_Option) {
    return this.entity_manager.save(ProductOptionEntity, {
      option_group: { id: option_group_id },
      ...options,
    });
  }

  async update(options: Product_Option, option_id: number) {
    return this.entity_manager.save(ProductOptionEntity, {
      id: option_id,
      ...options,
    });
  }

  async delete(id: number) {
    const { affected } = await this.entity_manager.delete(ProductOptionEntity, id);
    return !!affected;
  }
}
