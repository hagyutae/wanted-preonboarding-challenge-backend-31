import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Tag } from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ProductTagEntity } from "../entities";

@Injectable()
export default class ProductTagRepository implements IRepository<Product_Tag, ProductTagEntity> {
  constructor(private readonly entity_manager: EntityManager) {}

  async saves(product_tags: Product_Tag[]) {
    return await this.entity_manager.save(
      ProductTagEntity,
      product_tags.map(({ tag_id, product_id }) => ({
        product: { id: product_id },
        tag: { id: tag_id },
      })),
    );
  }

  save(param: Product_Tag): Promise<ProductTagEntity> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<ProductTagEntity | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<ProductTagEntity[]> {
    throw new Error("Method not implemented.");
  }
  update(param: Product_Tag, id: number): Promise<ProductTagEntity | void> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
