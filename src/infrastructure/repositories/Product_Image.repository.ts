import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Image } from "src/domain/entities";
import { ProductImageEntity } from "../entities";
import BaseRepository from "./BaseRepository";

@Injectable()
export default class ProductImageRepository extends BaseRepository<Product_Image> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async save({ product_id, option_id, ...image }: Product_Image): Promise<ProductImageEntity> {
    return await this.entity_manager.save(ProductImageEntity, {
      ...image,
      product: { id: product_id },
      option: { id: option_id ?? undefined },
    });
  }

  async saves(images: Product_Image[]): Promise<ProductImageEntity[]> {
    return await this.entity_manager.save(ProductImageEntity, images);
  }
}
