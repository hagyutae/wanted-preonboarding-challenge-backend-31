import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Image } from "src/domain/entities";
import { ProductImageEntity } from "../entities";

@Injectable()
export default class ProductImageRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save(image: Product_Image, product_id: number, option_id: number) {
    return await this.entity_manager.save(ProductImageEntity, {
      ...image,
      product: { id: product_id },
      option: { id: option_id },
    });
  }

  async saves(images: Product_Image[], product_id: number) {
    return await this.entity_manager.save(
      ProductImageEntity,
      images.map((image) => ({ image, product: { id: product_id } })),
    );
  }
}
