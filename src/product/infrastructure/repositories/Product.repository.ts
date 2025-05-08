import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { BaseRepository } from "src/libs/domain/repositories";
import { ProductCatalogDTO, ProductSummaryDTO } from "src/product/application/dto";
import { Product } from "src/product/domain/entities";
import { CategoryEntity, ProductCategoryEntity, ProductEntity } from "../entities";
import { ProductCatalogView, ProductSummaryView } from "../views";

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

  async find_by_filters({
    page,
    per_page,
    sort_field,
    sort_order,
    status,
    min_price,
    max_price,
    category: categories,
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
  }): Promise<ProductSummaryDTO[]> {
    // 카테고리 조인
    const inner_query = this.entity_manager
      .createQueryBuilder()
      .subQuery()
      .select("product_category.product_id")
      .from(ProductCategoryEntity, "product_category")
      .leftJoin(CategoryEntity, "category", "category.id = product_category.category_id")
      .where("category.id IN (:...categories)")
      .getQuery();

    // 상품 집계 처리 쿼리
    const query = this.entity_manager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .where(status ? "summary.status = :status" : "1=1", { status })
      .andWhere(min_price ? "summary.base_price >= :minPrice" : "1=1", { minPrice: min_price })
      .andWhere(max_price ? "summary.base_price <= :maxPrice" : "1=1", { maxPrice: max_price })
      .andWhere(categories ? `summary.id IN ${inner_query}` : "1=1")
      .andWhere(seller ? "summary.seller->>'id' = :seller" : "1=1", { seller })
      .andWhere(brand ? "summary.brand->>'id' = :brand" : "1=1", { brand })
      .andWhere(search ? "summary.name LIKE :search" : "1=1", { search: `%${search}%` })
      .orderBy(`summary.${sort_field}`, sort_order.toUpperCase() as "ASC" | "DESC")
      .offset((page - 1) * per_page)
      .limit(per_page)
      .setParameter("categories", categories);

    // 쿼리 실행
    return await query.getMany();
  }

  async find_by_id(id: number): Promise<ProductCatalogDTO | null> {
    return this.entity_manager.findOne(ProductCatalogView, { where: { id } });
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
