import { Injectable } from "@nestjs/common";
import { EntityManager, SelectQueryBuilder } from "typeorm";

import IRepository from "src/domain/repositories/IRepository";
import { CategoryEntity, ProductCategoryEntity } from "src/infrastructure/entities";
import { ProductSummaryView } from "src/infrastructure/views";

@Injectable()
export default class CategoryRepository implements IRepository<CategoryEntity, CategoryEntity> {
  constructor(private readonly entity_manager: EntityManager) {}

  async find_by_filters() {
    return this.entity_manager.find(CategoryEntity, {
      relations: ["parent"],
    });
  }

  async find_by_id(category_id: number, has_sub?: boolean) {
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

  save(param: CategoryEntity): Promise<CategoryEntity> {
    throw new Error("Method not implemented.");
  }
  saves(param: CategoryEntity[]): Promise<CategoryEntity[]> {
    throw new Error("Method not implemented.");
  }
  update(param: CategoryEntity, id: number): Promise<void | CategoryEntity> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
