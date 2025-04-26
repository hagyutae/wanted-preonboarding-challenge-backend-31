// 상품 API 응답 타입 정의
import { PaginatedData } from '../response';

/**
 * 브랜드 정보 응답 타입
 */
export interface BrandResponse {
  id: number;
  name: string;
  slug: string;
  description?: string;
  logo_url?: string;
  website?: string;
}

/**
 * 판매자 정보 응답 타입
 */
export interface SellerResponse {
  id: number;
  name: string;
  description?: string;
  logo_url?: string;
  rating?: number;
  contact_email?: string;
  contact_phone?: string;
}

/**
 * 이미지 정보 응답 타입
 */
export interface ProductImageResponse {
  id: number;
  url: string;
  alt_text?: string;
  is_primary: boolean;
  display_order: number;
  option_id?: number | null;
}

/**
 * 기본 상품 정보 응답 타입 (목록 조회용)
 */
export interface ProductListItemResponse {
  id: number;
  name: string;
  slug: string;
  short_description?: string;
  base_price: number;
  sale_price?: number;
  currency: string;
  primary_image?: {
    url: string;
    alt_text?: string;
  };
  brand: {
    id: number;
    name: string;
  };
  seller: {
    id: number;
    name: string;
  };
  rating?: number;
  review_count?: number;
  in_stock: boolean;
  status: string;
  created_at: string;
}

/**
 * 상품 목록 응답 타입
 */
export interface ProductListResponse extends PaginatedData<ProductListItemResponse> {}

/**
 * 상품 상세 정보의 상세 데이터 응답 타입
 */
export interface ProductDetailDataResponse {
  weight?: number;
  dimensions?: {
    width: number;
    height: number;
    depth: number;
  };
  materials?: string;
  country_of_origin?: string;
  warranty_info?: string;
  care_instructions?: string;
  additional_info?: Record<string, any>;
}

/**
 * 상품 상세 정보의 가격 데이터 응답 타입
 */
export interface ProductPriceDataResponse {
  base_price: number;
  sale_price?: number;
  currency: string;
  tax_rate?: number;
  discount_percentage?: number;
}

/**
 * 상품 카테고리 응답 타입
 */
export interface ProductCategoryResponse {
  id: number;
  name: string;
  slug: string;
  is_primary: boolean;
  parent?: {
    id: number;
    name: string;
    slug: string;
  };
}

/**
 * 상품 옵션 응답 타입
 */
export interface ProductOptionResponse {
  id: number;
  name: string;
  additional_price: number;
  sku?: string;
  stock: number;
  display_order: number;
}

/**
 * 상품 옵션 그룹 응답 타입
 */
export interface ProductOptionGroupResponse {
  id: number;
  name: string;
  display_order: number;
  options: ProductOptionResponse[];
}

/**
 * 상품 태그 응답 타입
 */
export interface ProductTagResponse {
  id: number;
  name: string;
  slug: string;
}

/**
 * 상품 평점 분포 응답 타입
 */
export interface ProductRatingDistributionResponse {
  [key: string]: number;
}

/**
 * 상품 평점 종합 응답 타입
 */
export interface ProductRatingResponse {
  average: number;
  count: number;
  distribution: ProductRatingDistributionResponse;
}

/**
 * 관련 상품 간소화 응답 타입
 */
export interface RelatedProductResponse {
  id: number;
  name: string;
  slug: string;
  short_description?: string;
  primary_image?: {
    url: string;
    alt_text?: string;
  };
  base_price: number;
  sale_price?: number;
  currency: string;
}

/**
 * 상품 상세 조회 응답 타입
 */
export interface ProductDetailResponse {
  id: number;
  name: string;
  slug: string;
  short_description?: string;
  full_description?: string;
  seller: SellerResponse;
  brand: BrandResponse;
  status: string;
  created_at: string;
  updated_at: string;
  detail: ProductDetailDataResponse;
  price: ProductPriceDataResponse;
  categories: ProductCategoryResponse[];
  option_groups: ProductOptionGroupResponse[];
  images: ProductImageResponse[];
  tags: ProductTagResponse[];
  rating: ProductRatingResponse;
  related_products?: RelatedProductResponse[];
}

/**
 * 상품 생성 응답 타입
 */
export interface ProductCreateResponse {
  id: number;
  name: string;
  slug: string;
  created_at: string;
  updated_at: string;
}

/**
 * 상품 수정 응답 타입
 */
export interface ProductUpdateResponse {
  id: number;
  name: string;
  slug: string;
  updated_at: string;
}

/**
 * 상품 옵션 생성/수정 응답 타입
 */
export interface ProductOptionModifyResponse {
  id: number;
  option_group_id: number;
  name: string;
  additional_price: number;
  sku?: string;
  stock: number;
  display_order: number;
}

/**
 * 상품 이미지 생성 응답 타입
 */
export interface ProductImageAddResponse {
  id: number;
  url: string;
  alt_text?: string;
  is_primary: boolean;
  display_order: number;
  option_id?: number | null;
} 