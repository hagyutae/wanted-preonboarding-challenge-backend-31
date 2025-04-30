import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Price } from "src/domain/entities";
import { ProductPriceEntity } from "../entities";

@Injectable()
export default class ProductPriceRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save({ product_id, ...price }: Product_Price) {
    return await this.entity_manager.save(ProductPriceEntity, {
      ...price,
      product: { id: product_id },
    });
  }

  async update({ product_id, ...price }: Product_Price) {
    return await this.entity_manager.update(
      ProductPriceEntity,
      { product: { id: product_id } },
      price,
    );
  }
}
