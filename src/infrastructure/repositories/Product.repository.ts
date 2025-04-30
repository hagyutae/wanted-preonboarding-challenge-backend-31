import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product } from "src/domain/entities";
import { ProductEntity } from "../entities";
import { ProductDetailView, ProductSummaryView } from "../views";

@Injectable()
export default class ProductRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save({
    product,
    seller_id,
    brand_id,
  }: {
    product: Product;
    seller_id: number;
    brand_id: number;
  }) {
    return this.entity_manager.save(ProductEntity, {
      ...product,
      seller: { id: seller_id },
      brand: { id: brand_id },
    });
  }

  async find_by_filters({
    page,
    per_page,
    sort_field,
    sort_order,
    status,
    min_price,
    max_price,
    category,
    seller,
    brand,
    search,
  }: {
    page: number;
    per_page: number;
    sort_field: string;
    sort_order: string;
    status?: string;
    min_price?: number;
    max_price?: number;
    category?: number[];
    seller?: number;
    brand?: number;
    search?: string;
  }) {
    // 상품 집계 처리 쿼리
    const query = this.entity_manager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .andWhere(status ? "summary.status = :status" : "1=1", { status })
      .andWhere(min_price ? "summary.base_price >= :minPrice" : "1=1", { minPrice: min_price })
      .andWhere(max_price ? "summary.base_price <= :maxPrice" : "1=1", { maxPrice: max_price })
      .andWhere(category ? "summary.id IN (:...category)" : "1=1", { category })
      .andWhere(seller ? "summary.seller->>'id' = :seller" : "1=1", { seller })
      .andWhere(brand ? "summary.brand->>'id' = :brand" : "1=1", { brand })
      .andWhere(search ? "summary.name LIKE :search" : "1=1", { search: `%${search}%` })
      .orderBy(`summary.${sort_field}`, sort_order.toUpperCase() as "ASC" | "DESC")
      .offset((page - 1) * per_page)
      .limit(per_page);

    // 쿼리 실행
    return await query.getMany();
  }

  async get_by_id(id: number) {
    return this.entity_manager.findOne(ProductDetailView, { where: { id } });
  }

  async update(
    id: number,
    {
      product,
      seller_id,
      brand_id,
    }: {
      product: Product;
      seller_id: number;
      brand_id: number;
    },
  ) {
    return await this.entity_manager.save(ProductEntity, {
      ...product,
      id,
      seller: { id: seller_id },
      brand: { id: brand_id },
    });
  }

  async delete(id: number) {
    await this.entity_manager.delete(ProductEntity, id);
  }
}
