import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Tag } from "src/domain/entities";
import { ProductTagEntity } from "../entities";

@Injectable()
export default class ProductTagRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save(product_id: number, product_tags: Omit<Product_Tag, "product_id">[]) {
    return await this.entity_manager.save(
      ProductTagEntity,
      product_tags.map(({ tag_id }) => ({
        product: { id: product_id },
        tag: { id: tag_id },
      })),
    );
  }
}
