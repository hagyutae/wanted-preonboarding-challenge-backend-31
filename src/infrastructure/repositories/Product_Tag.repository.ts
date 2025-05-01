import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Tag } from "src/domain/entities";
import { ProductTagEntity } from "../entities";
import BaseRepository from "./BaseRepository";

@Injectable()
export default class ProductTagRepository extends BaseRepository<Product_Tag> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async saves(product_tags: Product_Tag[]) {
    return await this.entity_manager.save(
      ProductTagEntity,
      product_tags.map(({ tag_id, product_id }) => ({
        product: { id: product_id },
        tag: { id: tag_id },
      })),
    );
  }
}
