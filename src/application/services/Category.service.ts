import { Injectable } from "@nestjs/common";
import { EntityManager, SelectQueryBuilder } from "typeorm";

import { CategoryEntity, ProductCategoryEntity } from "src/infrastructure/entities";
import { ProductSummaryView } from "src/infrastructure/views";
import { FilterDTO } from "../dto";

@Injectable()
export default class CategoryService {
  constructor(private readonly entityManager: EntityManager) {}

  async get_all_categories_as_tree(level: number = 1) {
    function build_tree(
      categories: CategoryEntity[],
      level: number, // 1: 대분류, 2: 중분류, 3: 소분류
      parent_id?: number,
    ) {
      if (level > 3) {
        return [];
      }

      const result = categories
        .filter((category) => category.parent?.id === parent_id)
        .map(({ id, parent, ...rest }) => {
          const children = build_tree(categories, level + 1, id);
          return {
            id,
            ...rest,
            ...(children.length > 0 && { children }),
          };
        });
      return result;
    }

    // 카테고리 정보 조회
    const categories = await this.entityManager.find(CategoryEntity, {
      relations: ["parent"],
    });

    // 카테고리 트리 구조로 변환
    return build_tree(categories, level);
  }

  async get_products_by_category_id(
    category_id: number,
    { page = 1, per_page = 10, sort = "created_at:desc", has_sub = true }: FilterDTO,
  ) {
    // 카테고리 정보 조회
    const category = await this.entityManager.findOne(CategoryEntity, {
      where: { id: category_id },
      relations: has_sub ? ["parent"] : undefined,
    });

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

    const query = this.entityManager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .where(inner_query)
      .setParameter("category_id", category_id)
      .orderBy(`summary.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * per_page)
      .take(per_page);

    const items = await query.getMany();

    // 페이지네이션 요약 정보
    const pagination = {
      total_items: items.length,
      total_pages: Math.ceil(items.length / per_page),
      current_page: page,
      per_page: per_page,
    };

    return {
      category,
      items,
      pagination,
    };
  }
}
