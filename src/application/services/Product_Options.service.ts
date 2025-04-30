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
    return this.entity_manager.save(ProductOptionEntity, {
      option_group: { id: option_group_id },
      ...options,
    });
  }

  async update_options(
    id: number,
    option_id: number,
    options: Product_Option,
  ): Promise<Product_Option> {
    return this.entity_manager.save(ProductOptionEntity, {
      id: option_id,
      ...options,
    });
  }

  async delete_options(id: number, option_id: number): Promise<void> {
    await this.entity_manager.delete(ProductOptionEntity, option_id);
  }

  async add_images(id: number, option_id: number, image: Product_Image): Promise<Product_Image> {
    const image_entity = await this.entity_manager.save(ProductImageEntity, {
      product: { id },
      option: { id: option_id },
      ...image,
    });

    // 이미지 저장 결과 반환
    return (({ product, option, ...rest }) => ({ ...rest }))(image_entity);
  }
}
