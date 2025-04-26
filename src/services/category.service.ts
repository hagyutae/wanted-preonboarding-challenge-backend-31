import { Prisma } from '@prisma/client';
import { categoryRepository } from '../repositories/category.repository';
import { getCategoryIds, isCategoryWithChildren } from '../utils/category';
import { PaginationParams } from '../utils/pagination';
import { CategoryWithChildrenResponse, ProductListItemResponse, CategoryBaseResponse } from '../types';

interface CategoryProductsResult {
  category: CategoryWithChildrenResponse;
  products: ProductListItemResponse[];
  total: number;
  page: number;
  perPage: number;
  totalPages: number;
}

export const categoryService = {
  /**
   * 모든 카테고리 조회
   * @param level 카테고리 레벨 필터 (선택사항)
   */
  async getAllCategories(level?: number): Promise<CategoryBaseResponse[]> {
    const categories = await categoryRepository.findAllRoot();
    
    // DB 모델을 API 응답 형식으로 변환
    return categories.map(category => ({
      id: category.id,
      name: category.name,
      slug: category.slug,
      description: category.description || undefined,
      level: category.level,
      image_url: category.imageUrl || undefined
    }));
  },

  /**
   * 특정 카테고리의 상품 목록 조회
   */
  async getCategoryProducts(
    categoryId: number,
    { page, perPage, sort }: PaginationParams,
    includeSubcategories: boolean
  ): Promise<CategoryProductsResult> {
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
    const dbProducts = await categoryRepository.findProductsByCategoryIds(
      categoryIds,
      (page - 1) * perPage,
      perPage,
      orderBy
    );
    
    // DB 모델을 API 응답 형식으로 변환 (샘플 변환)
    const products = dbProducts.map(product => ({
      id: product.id,
      name: product.name,
      slug: product.slug,
      description: product.shortDescription || undefined,
      base_price: product.price?.basePrice || 0,
      sale_price: product.price?.salePrice || undefined,
      currency: product.price?.currency || 'KRW',
      brand: {
        id: product.brand.id,
        name: product.brand.name
      },
      seller: {
        id: product.seller.id,
        name: product.seller.name
      },
      in_stock: product.stock?.quantity > 0,
      thumbnail: product.images.find(img => img.isPrimary)?.url || undefined,
      created_at: product.createdAt.toISOString(),
      updated_at: product.updatedAt.toISOString()
    }));

    // 카테고리 변환
    const categoryResponse: CategoryWithChildrenResponse = {
      id: category.id,
      name: category.name,
      slug: category.slug,
      description: category.description || undefined,
      level: category.level,
      image_url: category.imageUrl || undefined,
      children: category.children?.map(child => ({
        id: child.id,
        name: child.name,
        slug: child.slug,
        description: child.description || undefined,
        level: child.level,
        image_url: child.imageUrl || undefined
      }))
    };

    return {
      category: categoryResponse,
      products,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage)
    };
  }
}; 