import { Injectable } from "@nestjs/common";
import { EntityManager, SelectQueryBuilder } from "typeorm";

import { Product_Category } from "src/domain/entities";
import { CategoryEntity, ProductCategoryEntity } from "src/infrastructure/entities";
import { ProductSummaryView } from "src/infrastructure/views";

@Injectable()
export default class CategoryRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save(categories: Product_Category[], product_id: number) {
    return await this.entity_manager.save(
      ProductCategoryEntity,
      categories.map(({ category_id, is_primary }) => ({
        category: { id: category_id } as ProductCategoryEntity,
        is_primary,
        product: { id: product_id } as ProductCategoryEntity,
      })),
    );
  }

  async get_all_categories() {
    return this.entity_manager.find(CategoryEntity, {
      relations: ["parent"],
    });
  }

  async get({ category_id, has_sub }: { category_id: number; has_sub?: boolean }) {
    return this.entity_manager.findOne(CategoryEntity, {
      where: { id: category_id },
      relations: has_sub ? ["parent"] : undefined,
    });
  }

  async get_products_by_category_id({
    category_id,
    page = 1,
    per_page = 10,
    sort = "created_at:desc",
  }) {
    // 카테고리 조인
    const inner_query = (qb: SelectQueryBuilder<ProductCategoryEntity>) =>
      "summary.id IN " +
      qb
        .subQuery()
        .select("product_category.product_id")
        .from(ProductCategoryEntity, "product_category")
        .leftJoin(CategoryEntity, "category", "category.id = product_category.category_id")
        .where("category.id = :category_id")
        .getQuery();

    // 필터링
    const [field, order] = sort.split(":");

    const query = this.entity_manager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .where(inner_query)
      .setParameter("category_id", category_id)
      .orderBy(`summary.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * per_page)
      .take(per_page);

    return await query.getMany();
  }
}
