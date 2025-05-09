// 카테고리 API 응답 타입 정의
import { PaginatedData } from '../response';
import { ProductListItemResponse } from './product.response';

/**
 * 카테고리 기본 정보 응답 타입
 */
export interface CategoryBaseResponse {
  id: number;
  name: string;
  slug: string;
  description?: string;
  level: number;
  image_url?: string;
}

/**
 * 하위 카테고리를 포함하는 카테고리 응답 타입 (재귀적 구조)
 */
export interface CategoryWithChildrenResponse extends CategoryBaseResponse {
  children?: CategoryWithChildrenResponse[];
  parent?: {
    id: number;
    name: string;
    slug: string;
  };
}

/**
 * 특정 카테고리의 상품 목록 조회 응답 타입
 */
export interface CategoryProductsResponse {
  category: CategoryWithChildrenResponse;
  items: ProductListItemResponse[];
  pagination: {
    total_items: number;
    total_pages: number;
    current_page: number;
    per_page: number;
  };
}

/**
 * 메인페이지용 카테고리 정보 응답 타입
 */
export interface FeaturedCategoryResponse {
  id: number;
  name: string;
  slug: string;
  image_url?: string;
  product_count: number;
} 