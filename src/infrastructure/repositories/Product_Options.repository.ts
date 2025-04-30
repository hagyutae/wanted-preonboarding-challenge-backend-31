import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Option } from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ProductOptionEntity } from "../entities";

@Injectable()
export default class ProductOptionsRepository
  implements IRepository<Product_Option, ProductOptionEntity>
{
  constructor(private readonly entity_manager: EntityManager) {}

  async save({ option_group_id, ...options }: Product_Option): Promise<ProductOptionEntity> {
    return this.entity_manager.save(ProductOptionEntity, {
      option_group: { id: option_group_id },
      ...options,
    });
  }

  async update(options: Product_Option, option_id: number): Promise<ProductOptionEntity> {
    return this.entity_manager.save(ProductOptionEntity, {
      id: option_id,
      ...options,
    });
  }

  async delete(id: number): Promise<void> {
    await this.entity_manager.delete(ProductOptionEntity, id);
  }

  saves(param: Product_Option[]): Promise<ProductOptionEntity[]> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<ProductOptionEntity | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<ProductOptionEntity[]> {
    throw new Error("Method not implemented.");
  }
}
