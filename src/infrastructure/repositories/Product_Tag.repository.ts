import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { ProductTagEntity } from "../entities";

@Injectable()
export default class ProductTagRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save(tag_ids: number[], product_id: number) {
    return await this.entity_manager.save(
      ProductTagEntity,
      tag_ids.map((tag_id) => ({
        product: { id: product_id },
        tag: { id: tag_id },
      })),
    );
  }
}
