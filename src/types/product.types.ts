import { Prisma } from '@prisma/client';

// 상품 목록 조회를 위한 필터 파라미터
export interface ProductFilterParams {
  status?: string;
  minPrice?: number;
  maxPrice?: number;
  category?: number[];
  seller?: number;
  brand?: number;
  inStock?: boolean;
  tags?: number[];
  createdAtStart?: Date;
  createdAtEnd?: Date;
  search?: string;
}

// 상품 목록 정렬 옵션
export interface ProductSortParams {
  field: string;
  direction: 'asc' | 'desc';
}

// 상품 목록 조회를 위한 쿼리 파라미터
export interface ProductQueryParams {
  page?: number;
  perPage?: number;
  sort?: string; // 형식: {필드}:{asc|desc} (예: 'created_at:desc')
  status?: string;
  minPrice?: string;
  maxPrice?: string;
  category?: string; // 콤마로 구분된 카테고리 ID 목록
  seller?: string;
  brand?: string;
  inStock?: string;
  search?: string;
  tags?: string; // 콤마로 구분된 태그 ID 목록
  createdAtStart?: string;
  createdAtEnd?: string;
}

// ... 기존 타입들 