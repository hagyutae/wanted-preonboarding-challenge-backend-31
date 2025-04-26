import { Prisma } from '@prisma/client';
import { categoryRepository } from '../repositories/category.repository';
import { getCategoryIds, isCategoryWithChildren } from '../utils/category';
import { PaginationParams } from '../utils/pagination';

export const categoryService = {
  /**
   * 모든 카테고리 조회
   */
  async getAllCategories() {
    return categoryRepository.findAllRoot();
  },

  /**
   * 특정 카테고리의 상품 목록 조회
   */
  async getCategoryProducts(
    categoryId: number,
    { page, perPage, sort }: PaginationParams,
    includeSubcategories: boolean
  ) {
    // 카테고리 조회
    const category = await categoryRepository.findById(categoryId);

    if (!category || !isCategoryWithChildren(category)) {
      throw new Error('카테고리를 찾을 수 없습니다.');
    }

    // 카테고리 ID 목록 준비
    const categoryIds = getCategoryIds(category, includeSubcategories);

    // 정렬 파라미터 파싱
    const [sortField, sortOrder] = sort.split(':');
    const orderBy: Prisma.ProductOrderByWithRelationInput = {
      [sortField]: sortOrder === 'asc' ? 'asc' : 'desc'
    };

    // 전체 상품 수 계산
    const total = await categoryRepository.countProducts(categoryIds);

    // 상품 조회
    const products = await categoryRepository.findProductsByCategoryIds(
      categoryIds,
      (page - 1) * perPage,
      perPage,
      orderBy
    );

    return {
      products,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage)
    };
  }
}; 