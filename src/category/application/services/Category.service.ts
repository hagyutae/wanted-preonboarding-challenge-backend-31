import { Inject, Injectable, NotFoundException } from "@nestjs/common";

import { IBaseRepository } from "@libs/domain/repositories";
import { ProductSummaryDTO } from "@product/application/dto";
import { Category } from "@category/domain/entities";

@Injectable()
export default class CategoryService {
  constructor(
    @Inject("ICategoryRepository")
    private readonly repository: IBaseRepository<Category>,
    @Inject("IProductRepository")
    private readonly product_repository: IBaseRepository<ProductSummaryDTO>,
  ) {}

  async find_all_as_tree(level: number = 1) {
    function build_tree(
      categories: Category[],
      level: number, // 1: 대분류, 2: 중분류, 3: 소분류
      parent_id?: number,
    ) {
      if (level > 3) {
        return [];
      }

      const result: (Category | { id: number; children: Category })[] = categories
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
    const categories = await this.repository.find_by_filters({});

    // 카테고리 트리 구조로 변환
    return build_tree(categories, level);
  }

  async find_products_by_category_id(
    category_id: number,
    { page = 1, per_page = 10, sort = "created_at:desc", has_sub = true },
  ) {
    const [sort_field, sort_order] = sort?.split(":") ?? ["created_at", "DESC"];

    // 카테고리 정보 조회
    const category = await this.repository.find_by_id(category_id);
    if (!category) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Category", resourceId: category_id },
      });
    }
    if (!has_sub) {
      delete category?.parent;
    }

    // 아이템 필터링
    const items = await this.product_repository.find_by_filters({
      page,
      per_page,
      sort_field,
      sort_order,
      category: [category_id],
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
