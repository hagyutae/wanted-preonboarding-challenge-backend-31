import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Image, Product_Option } from "src/domain/entities";
import { ProductImageEntity, ProductOptionEntity } from "src/infrastructure/entities";

@Injectable()
export default class ProductOptionsService {
  constructor(private readonly entity_manager: EntityManager) {}

  async add_options(
    id: number,
    option_group_id: number,
    options: Product_Option,
  ): Promise<Product_Option> {
    const option_entity = this.entity_manager.create(ProductOptionEntity, {
      option_group: { id: option_group_id },
      ...options,
    });

    return await this.entity_manager.save(ProductOptionEntity, option_entity);
  }

  async update_options(
    id: number,
    option_id: number,
    options: Product_Option,
  ): Promise<Product_Option> {
    const option_entity = await this.entity_manager.findOne(ProductOptionEntity, {
      where: { id: option_id },
    });

    if (!option_entity) {
      throw new Error(`Option with id ${option_id} not found`);
    }

    const updated_option_entity = this.entity_manager.merge(
      ProductOptionEntity,
      option_entity,
      options,
    );

    return await this.entity_manager.save(updated_option_entity);
  }

  async delete_options(id: number, option_id: number): Promise<void> {
    await this.entity_manager.delete(ProductOptionEntity, option_id);
  }

  async add_images(id: number, option_id: number, image: Product_Image): Promise<Product_Image> {
    const productImage = this.entity_manager.create(ProductImageEntity, {
      ...image,
      option: { id: option_id },
      product: { id },
    });

    return await this.entity_manager.save(ProductImageEntity, productImage);
  }
}
