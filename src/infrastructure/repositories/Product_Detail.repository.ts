import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Detail } from "src/domain/entities";
import { ProductDetailEntity } from "../entities";
import BaseRepository from "./BaseRepository";

@Injectable()
export default class ProductDetailRepository extends BaseRepository<
  Product_Detail,
  ProductDetailEntity
> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async save({ product_id, ...detail }: Product_Detail) {
    return await this.entity_manager.save(ProductDetailEntity, {
      ...detail,
      product: { id: product_id },
    });
  }

  async update({ product_id, ...detail }: Product_Detail) {
    await this.entity_manager.update(ProductDetailEntity, { product: { id: product_id } }, detail);
  }
}
