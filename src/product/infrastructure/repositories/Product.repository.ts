import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { BaseRepository } from "@libs/domain/repositories";
import { ProductCatalogDTO, ProductSummaryDTO } from "@product/application/dto";
import { Product } from "@product/domain/entities";
import { ProductEntity } from "../entities";

@Injectable()
export default class ProductRepository extends BaseRepository<
  Product | ProductSummaryDTO | ProductCatalogDTO
> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async save({ seller_id, brand_id, ...product }: Product): Promise<Product> {
    return this.entity_manager.save(ProductEntity, {
      ...product,
      seller: { id: seller_id },
      brand: { id: brand_id },
    });
  }

  async update({ seller_id, brand_id, ...product }: Product, id: number) {
    const { affected } = await this.entity_manager.update(
      ProductEntity,
      {
        id,
      },
      { ...product, seller: { id: seller_id }, brand: { id: brand_id } },
    );
    return !!affected;
  }

  async delete(id: number) {
    const { affected } = await this.entity_manager.delete(ProductEntity, id);
    return !!affected;
  }
}
