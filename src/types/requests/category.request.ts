// 카테고리 API 요청 타입 정의

/**
 * 카테고리 목록 조회 요청 파라미터
 */
export interface GetCategoriesRequest {
  level?: number;
}

/**
 * 카테고리 상품 목록 조회 요청 파라미터
 */
export interface GetCategoryProductsRequest {
  page?: number;
  perPage?: number;
  sort?: string;
  includeSubcategories?: boolean;
} 