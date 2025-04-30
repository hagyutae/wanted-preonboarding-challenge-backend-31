import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Image } from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ProductImageEntity } from "../entities";

@Injectable()
export default class ProductImageRepository
  implements IRepository<Product_Image, ProductImageEntity>
{
  constructor(private readonly entity_manager: EntityManager) {}

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

  find_by_id(id: number): Promise<ProductImageEntity | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<ProductImageEntity[]> {
    throw new Error("Method not implemented.");
  }
  update(param: Product_Image, id: number): Promise<ProductImageEntity | void> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
