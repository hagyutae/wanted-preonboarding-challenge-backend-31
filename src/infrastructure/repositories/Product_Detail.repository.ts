import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Detail } from "src/domain/entities";
import { ProductDetailEntity } from "../entities";

@Injectable()
export default class ProductDetailRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save({ product_id, ...detail }: Product_Detail) {
    return await this.entity_manager.save(ProductDetailEntity, {
      ...detail,
      product: { id: product_id },
    });
  }

  async update({ product_id, ...detail }: Product_Detail) {
    return await this.entity_manager.update(
      ProductDetailEntity,
      { product: { id: product_id } },
      detail,
    );
  }
}
