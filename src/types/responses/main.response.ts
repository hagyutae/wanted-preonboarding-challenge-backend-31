// 메인 페이지 API 응답 타입 정의
import { ProductListItemResponse } from './product.response';
import { FeaturedCategoryResponse } from './category.response';

/**
 * 메인 페이지 응답 데이터 타입
 */
export interface MainPageDataResponse {
  new_products: ProductListItemResponse[];
  popular_products: ProductListItemResponse[];
  featured_categories: FeaturedCategoryResponse[];
} 