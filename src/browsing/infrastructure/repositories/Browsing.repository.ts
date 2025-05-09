import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { IBrowsingRepository } from "@libs/domain/repositories";
import { ProductCategoryEntity, ProductEntity } from "@product/infrastructure/entities";
import { CategoryEntity } from "@category/infrastructure/entities";
import { ProductCatalogView, ProductSummaryView } from "@browsing/infrastructure/views";
import { ProductCatalogDTO, ProductSummaryDTO } from "@browsing/presentation/dto";

@Injectable()
export default class BrowsingRepository implements IBrowsingRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async find_by_filters({
    page,
    per_page,
    sort_field,
    sort_order,
    status,
    category: categories,
    min_price,
    max_price,
    seller,
    brand,
    search,
  }: {
    page?: number;
    per_page?: number;
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
      .setParameter("categories", categories);

    if (page && per_page) {
      query.offset((page - 1) * per_page).limit(per_page);
    }

    // 쿼리 실행
    return await query.getMany();
  }

  async find_by_id(id: number): Promise<ProductCatalogDTO | null> {
    return this.entity_manager.findOne(ProductCatalogView, { where: { id } });
  }

  async get_featured_categories(): Promise<CategoryEntity[]> {
    const query = this.entity_manager
      .getRepository(CategoryEntity)
      .createQueryBuilder("categories")
      .innerJoinAndSelect(
        ProductCategoryEntity,
        "product_categories",
        "product_categories.category_id = categories.id",
      )
      .innerJoinAndSelect(ProductEntity, "products", "products.id = product_categories.product_id")
      .select([
        "categories.id as id",
        "categories.name as name",
        "categories.slug as slug",
        "categories.image_url as image_url",
      ])
      .addSelect("COUNT(products.id)", "product_count")
      .groupBy("categories.id")
      .orderBy("product_count", "DESC")
      .limit(5);

    return query.getRawMany();
  }
}
