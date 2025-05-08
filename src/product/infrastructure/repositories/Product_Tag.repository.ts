import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { BaseRepository } from "src/libs/domain/repositories";
import { ProductTagDTO } from "src/product/application/dto";
import { ProductTagEntity } from "../entities";

@Injectable()
export default class ProductTagRepository extends BaseRepository<ProductTagDTO> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async saves(product_tags: ProductTagDTO[]) {
    return await this.entity_manager.save(
      ProductTagEntity,
      product_tags.map(({ tag_id, product_id }) => ({
        product: { id: product_id },
        tag: { id: tag_id },
      })),
    );
  }
}
