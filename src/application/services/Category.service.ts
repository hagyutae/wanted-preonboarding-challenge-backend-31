import { Injectable } from "@nestjs/common";

import { CategoryEntity } from "src/infrastructure/entities";
import { CategoryRepository } from "src/infrastructure/repositories";
import { FilterDTO } from "../dto";

@Injectable()
export default class CategoryService {
  constructor(private readonly repository: CategoryRepository) {}

  async find_all_as_tre(level: number = 1) {
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
    const categories = await this.repository.get_all_categories();

    // 카테고리 트리 구조로 변환
    return build_tree(categories, level);
  }

  async find_products_by_category_id(
    category_id: number,
    { page = 1, per_page = 10, sort = "created_at:desc", has_sub = true }: FilterDTO,
  ) {
    // 카테고리 정보 조회
    const category = await this.repository.get({
      category_id,
      has_sub,
    });

    const items = await this.repository.get_products_by_category_id({
      category_id,
      page,
      per_page,
      sort,
    });

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
