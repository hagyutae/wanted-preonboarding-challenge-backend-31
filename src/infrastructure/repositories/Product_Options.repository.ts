import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Image, Product_Option } from "src/domain/entities";
import { ProductImageEntity, ProductOptionEntity } from "../entities";

@Injectable()
export default class ProductOptionsRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save(
    id: number,
    option_group_id: number,
    options: Product_Option,
  ): Promise<Product_Option> {
    return this.entity_manager.save(ProductOptionEntity, {
      option_group: { id: option_group_id },
      ...options,
    });
  }

  async update(id: number, option_id: number, options: Product_Option): Promise<Product_Option> {
    return this.entity_manager.save(ProductOptionEntity, {
      id: option_id,
      ...options,
    });
  }

  async delete(id: number, option_id: number): Promise<void> {
    await this.entity_manager.delete(ProductOptionEntity, option_id);
  }

  async save_images(id: number, option_id: number, image: Product_Image) {
    return this.entity_manager.save(ProductImageEntity, {
      product: { id },
      option: { id: option_id },
      ...image,
    });
  }
}
