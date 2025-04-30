import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Image } from "src/domain/entities";
import { ProductImageEntity } from "../entities";

@Injectable()
export default class ProductImageRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save({ product_id, option_id, ...image }: Product_Image) {
    return await this.entity_manager.save(ProductImageEntity, {
      ...image,
      product: { id: product_id },
      option: { id: option_id ?? undefined },
    });
  }

  async saves(images: Product_Image[]) {
    return await this.entity_manager.save(ProductImageEntity, images);
  }
}
