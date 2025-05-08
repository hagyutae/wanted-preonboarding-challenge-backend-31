import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { BaseRepository } from "@libs/domain/repositories";
import { Product_Price } from "@product/domain/entities";
import { ProductPriceEntity } from "../entities";

@Injectable()
export default class ProductPriceRepository extends BaseRepository<Product_Price> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async save({ product_id, ...price }: Product_Price) {
    return await this.entity_manager.save(ProductPriceEntity, {
      ...price,
      product: { id: product_id },
    });
  }

  async update({ product_id, ...price }: Product_Price) {
    const { affected } = await this.entity_manager.update(
      ProductPriceEntity,
      { product: { id: product_id } },
      price,
    );
    return !!affected;
  }
}
