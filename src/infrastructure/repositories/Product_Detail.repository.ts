import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Detail } from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ProductDetailEntity } from "../entities";

@Injectable()
export default class ProductDetailRepository
  implements IRepository<Product_Detail, ProductDetailEntity>
{
  constructor(private readonly entity_manager: EntityManager) {}

  async save({ product_id, ...detail }: Product_Detail) {
    return await this.entity_manager.save(ProductDetailEntity, {
      ...detail,
      product: { id: product_id },
    });
  }

  async update({ product_id, ...detail }: Product_Detail) {
    await this.entity_manager.update(ProductDetailEntity, { product: { id: product_id } }, detail);
  }

  saves(param: Product_Detail[]): Promise<ProductDetailEntity[]> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<ProductDetailEntity | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<ProductDetailEntity[]> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
