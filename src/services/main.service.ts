import { productRepository } from '../repositories/product.repository';
import { categoryRepository } from '../repositories/category.repository';
import { ProductListItemResponse, FeaturedCategoryResponse } from '../types';

export const mainService = {
  /**
   * 신규 상품 목록 조회
   */
  async getNewProducts(limit = 5): Promise<ProductListItemResponse[]> {
    return productRepository.findAll(0, limit);
  },

  /**
   * 인기 상품 목록 조회
   * 실제로는 리뷰 수, 판매량 등을 기준으로 해야 하지만 
   * 여기서는 임시로 리뷰 수가 많은 상품을 조회합니다.
   */
  async getPopularProducts(limit = 5): Promise<ProductListItemResponse[]> {
    // TODO: 인기 상품 조회 로직 구현
    return []; // 임시
  },

  /**
   * 주요 카테고리 목록 조회
   */
  async getFeaturedCategories(limit = 5): Promise<FeaturedCategoryResponse[]> {
    const categories = await categoryRepository.findAllRoot();
    
    // 카테고리를 API 응답 형식으로 변환
    return categories.map(category => {
      return {
        id: category.id,
        name: category.name,
        slug: category.slug,
        image_url: category.imageUrl || undefined,
        product_count: 0 // TODO: 실제 상품 개수 계산
      };
    });
  }
}; 