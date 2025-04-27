// 상품 API 요청 타입 정의

/**
 * 상품 목록 조회 요청 파라미터
 */
export interface GetProductsRequest {
  page?: number;
  perPage?: number;
  sort?: string;
  status?: string;
  minPrice?: number;
  maxPrice?: number;
  category?: number | number[];
  seller?: number;
  brand?: number;
  inStock?: boolean;
  search?: string;
}

/**
 * 상품 등록 시 가격 정보
 */
export interface ProductPriceInput {
  base_price: number;
  sale_price?: number;
  cost_price?: number;
  currency?: string;
  tax_rate?: number;
}

/**
 * 상품 등록 시 상세 정보
 */
export interface ProductDetailInput {
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
  additional_info?: Record<string, string | number | boolean | null>;
}

/**
 * 상품 등록 시 카테고리 정보
 */
export interface ProductCategoryInput {
  category_id: number;
  is_primary: boolean;
}

/**
 * 상품 등록 시 옵션 정보
 */
export interface ProductOptionInput {
  name: string;
  additional_price?: number;
  sku?: string;
  stock: number;
  display_order?: number;
}

/**
 * 상품 등록 시 옵션 그룹 정보
 */
export interface ProductOptionGroupInput {
  name: string;
  display_order?: number;
  options: ProductOptionInput[];
}

/**
 * 상품 등록 시 이미지 정보
 */
export interface ProductImageInput {
  url: string;
  alt_text?: string;
  is_primary?: boolean;
  display_order?: number;
  option_id?: number | null;
}

/**
 * 상품 등록 요청 본문
 */
export interface CreateProductRequest {
  name: string;
  slug: string;
  short_description?: string;
  full_description?: string;
  seller_id: number;
  brand_id: number;
  status: string;
  detail: ProductDetailInput;
  price: ProductPriceInput;
  categories: ProductCategoryInput[];
  option_groups?: ProductOptionGroupInput[];
  images?: ProductImageInput[];
  tags?: number[];
}

/**
 * 상품 수정 요청 본문 (등록과 동일하나 모든 필드가 선택적)
 */
export type UpdateProductRequest = Partial<CreateProductRequest>;

/**
 * 상품 옵션 추가 요청 본문
 */
export interface AddProductOptionRequest {
  option_group_id: number;
  name: string;
  additional_price?: number;
  sku?: string;
  stock: number;
  display_order?: number;
}

/**
 * 상품 옵션 수정 요청 본문
 */
export type UpdateProductOptionRequest = Partial<AddProductOptionRequest>;

/**
 * 상품 이미지 추가 요청 본문
 */
export interface AddProductImageRequest {
  url: string;
  alt_text?: string;
  is_primary?: boolean;
  display_order?: number;
  option_id?: number | null;
}

/**
 * 상품 리뷰 작성 요청 본문
 */
export interface CreateProductReviewRequest {
  rating: number;
  title?: string;
  content?: string;
}
