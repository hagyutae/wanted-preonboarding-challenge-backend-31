import { Category } from '@prisma/client';
import { CategoryBaseResponse, CategoryWithChildrenResponse } from '../responses/category.response';

export type CategoryWithRelations = Category & {
  children?: CategoryWithRelations[];
  parent?: Category;
  _count?: {
    products: number;
  };
};

/**
 * 카테고리 DB 모델을 기본 API 응답으로 변환
 */
export function mapCategoryToBasicResponse(category: CategoryWithRelations): CategoryBaseResponse {
  return {
    id: category.id,
    name: category.name,
    slug: category.slug,
    description: category.description ?? undefined,
    level: category.level,
    image_url: category.imageUrl ?? undefined,
  };
}

/**
 * 카테고리 DB 모델을 자식 요소를 포함한 API 응답으로 변환
 */
export function mapCategoryToResponse(
  category: CategoryWithRelations,
): CategoryWithChildrenResponse {
  const response: CategoryWithChildrenResponse = {
    ...mapCategoryToBasicResponse(category),
    parent: category.parent
      ? {
          id: category.parent.id,
          name: category.parent.name,
          slug: category.parent.slug,
        }
      : undefined,
  };

  // 자식 카테고리가 있으면 재귀적으로 변환
  if (category.children && category.children.length > 0) {
    response.children = category.children.map((child) => mapCategoryToResponse(child));
  } else {
    response.children = [];
  }

  return response;
}
