import { EntityManager } from "typeorm";
import { Injectable } from "@nestjs/common";

import { Product_Image, Product_Option } from "src/domain";
import { ProductImageEntity, ProductOptionEntity } from "src/infrastructure/entities";

@Injectable()
export default class ProductOptionsService {
  constructor(private readonly entityManager: EntityManager) {}

  async addOptions(
    id: number,
    option_group_id: number,
    options: Product_Option,
  ): Promise<Product_Option> {
    const optionEntity = this.entityManager.create(ProductOptionEntity, {
      option_group: { id: option_group_id },
      ...options,
    });

    return await this.entityManager.save(ProductOptionEntity, optionEntity);
  }

  async updateOptions(
    id: number,
    option_id: number,
    options: Product_Option,
  ): Promise<Product_Option> {
    const optionEntity = await this.entityManager.findOne(ProductOptionEntity, {
      where: { id: option_id },
    });

    if (!optionEntity) {
      throw new Error(`Option with id ${option_id} not found`);
    }

    const updatedOptionsEntity = this.entityManager.merge(
      ProductOptionEntity,
      optionEntity,
      options,
    );

    return await this.entityManager.save(updatedOptionsEntity);
  }

  async deleteOptions(id: number, option_id: number): Promise<void> {
    await this.entityManager.delete(ProductOptionEntity, option_id);
  }

  async addImages(id: number, option_id: number, image: Product_Image): Promise<Product_Image> {
    const productImage = this.entityManager.create(ProductImageEntity, {
      ...image,
      option: { id: option_id },
      product: { id },
    });

    return await this.entityManager.save(ProductImageEntity, productImage);
  }
}
