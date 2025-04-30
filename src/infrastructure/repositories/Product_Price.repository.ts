import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Price } from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ProductPriceEntity } from "../entities";

@Injectable()
export default class ProductPriceRepository
  implements IRepository<Product_Price, ProductPriceEntity>
{
  constructor(private readonly entity_manager: EntityManager) {}

  async save({ product_id, ...price }: Product_Price): Promise<ProductPriceEntity> {
    return await this.entity_manager.save(ProductPriceEntity, {
      ...price,
      product: { id: product_id },
    });
  }

  async update({ product_id, ...price }: Product_Price) {
    await this.entity_manager.update(ProductPriceEntity, { product: { id: product_id } }, price);
  }

  saves(param: Product_Price[]): Promise<ProductPriceEntity[]> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<ProductPriceEntity | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<ProductPriceEntity[]> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
