import { Prisma } from '@prisma/client';
import { categoryRepository } from '../repositories/category.repository';
import { getCategoryIds, isCategoryWithChildren } from '../utils/category';
import { PaginationParams } from '../utils/pagination';
import { 
  CategoryWithChildrenResponse, 
  ProductListItemResponse, 
  CategoryBaseResponse 
} from '../types';
import { productRepository } from '../repositories/product.repository';

interface CategoryProductsResult {
  category: CategoryWithChildrenResponse;
  products: ProductListItemResponse[];
  total: number;
  page: number;
  perPage: number;
  totalPages: number;
}

/**
 * 카테고리를 API 응답 형식으로 변환하는 재귀 함수
 */
function mapCategoryToResponse(category: any): CategoryWithChildrenResponse {
  const response: CategoryWithChildrenResponse = {
    id: category.id,
    name: category.name,
    slug: category.slug,
    description: category.description || undefined,
    level: category.level,
    image_url: category.imageUrl || undefined
  };

  // 부모 카테고리가 있는 경우 추가
  if (category.parent) {
    response.parent = {
      id: category.parent.id,
      name: category.parent.name,
      slug: category.parent.slug
    };
  }

  // 자식 카테고리가 있는 경우 재귀적으로 변환
  if (category.children && category.children.length > 0) {
    response.children = category.children.map((child: any) => mapCategoryToResponse(child));
  }

  return response;
}

export const categoryService = {
  /**
   * 모든 카테고리 조회
   * @param level 카테고리 레벨 필터 (선택사항)
   */
  async getAllCategories(level?: number): Promise<CategoryWithChildrenResponse[]> {
    const categories = await categoryRepository.findAll(level);
    
    // DB 모델을 API 응답 형식으로 변환
    return categories.map(category => mapCategoryToResponse(category));
  },

  /**
   * 특정 카테고리의 상품 목록 조회
   */
  async getCategoryProducts(
    categoryId: number,
    { page, perPage }: PaginationParams,
    includeSubcategories: boolean
  ): Promise<CategoryProductsResult> {
    // 카테고리 조회
    const category = await categoryRepository.findById(categoryId);

    if (!category || !isCategoryWithChildren(category)) {
      throw new Error('카테고리를 찾을 수 없습니다.');
    }

    // 카테고리 ID 목록 준비
    const categoryIds = getCategoryIds(category, includeSubcategories);
    
    // 전체 상품 수 계산
    const total = await categoryRepository.countProducts(categoryIds);

    // 상품 조회
    const products = await categoryRepository.findProductsByCategoryIds(
      categoryIds,
      (page - 1) * perPage,
      perPage,
      { createdAt: 'desc' }
    );
    
    // DB 모델을 API 응답 형식으로 변환
    const productResponses = products.map(product => {
      const primaryImage = product.images.find(img => img.isPrimary);
      
      // 리뷰 평점 및 개수 계산
      const reviewCount = 0; // TODO: 리뷰 수 구현
      const averageRating = 0; // TODO: 평점 구현
      
      // 재고 확인
      const hasStock = product.optionGroups.some(group => 
        group.options.some(option => option.stock > 0)
      );
      
      return {
        id: product.id,
        name: product.name,
        slug: product.slug,
        short_description: product.shortDescription || undefined,
        base_price: product.price?.basePrice || 0,
        sale_price: product.price?.salePrice || undefined,
        currency: product.price?.currency || 'KRW',
        primary_image: primaryImage ? {
          url: primaryImage.url,
          alt_text: primaryImage.altText || undefined
        } : undefined,
        brand: {
          id: product.brand.id,
          name: product.brand.name
        },
        seller: {
          id: product.seller.id,
          name: product.seller.name
        },
        rating: averageRating || undefined,
        review_count: reviewCount,
        in_stock: hasStock,
        status: product.status,
        created_at: product.createdAt.toISOString()
      };
    });

    // 카테고리 변환
    const categoryResponse = mapCategoryToResponse(category);

    return {
      category: categoryResponse,
      products: productResponses,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage)
    };
  }
}; 