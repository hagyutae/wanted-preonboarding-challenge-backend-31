import { ProductFilterParams, ProductSortParams, ProductQueryParams } from '../types/product.types';

/**
 * Request 쿼리 파라미터를 ProductFilterParams로 변환
 */
export const parseFilterParams = (query: ProductQueryParams): ProductFilterParams => {
  const filterParams: ProductFilterParams = {};

  // 상태 필터
  if (query.status) {
    filterParams.status = query.status;
  }

  // 가격 범위 필터
  if (query.minPrice) {
    filterParams.minPrice = parseFloat(query.minPrice);
  }
  if (query.maxPrice) {
    filterParams.maxPrice = parseFloat(query.maxPrice);
  }

  // 카테고리 필터
  if (query.category) {
    filterParams.category = query.category.split(',').map(Number);
  }

  // 판매자 필터
  if (query.seller) {
    filterParams.seller = parseInt(query.seller);
  }

  // 브랜드 필터
  if (query.brand) {
    filterParams.brand = parseInt(query.brand);
  }

  // 재고 유무 필터
  if (query.inStock) {
    filterParams.inStock = query.inStock.toLowerCase() === 'true';
  }

  // 태그 필터
  if (query.tags) {
    filterParams.tags = query.tags.split(',').map(Number);
  }

  // 등록일 범위 필터
  if (query.createdAtStart) {
    filterParams.createdAtStart = new Date(query.createdAtStart);
  }
  if (query.createdAtEnd) {
    filterParams.createdAtEnd = new Date(query.createdAtEnd);
  }

  // 검색어
  if (query.search) {
    filterParams.search = query.search;
  }

  return filterParams;
};

/**
 * Request 쿼리 파라미터에서 정렬 옵션 파싱
 */
export const parseSortParams = (query: ProductQueryParams): ProductSortParams[] => {
  const defaultSort: ProductSortParams = { field: 'createdAt', direction: 'desc' };

  if (!query.sort) {
    return [defaultSort];
  }

  return query.sort.split(',').map((sortItem) => {
    const [field, direction] = sortItem.split(':');

    // 필드 매핑 (snake_case를 camelCase로 변환)
    const fieldMap: Record<string, string> = {
      created_at: 'createdAt',
      updated_at: 'updatedAt',
      base_price: 'price.basePrice',
      sale_price: 'price.salePrice',
      rating: 'rating',
      name: 'name',
    };

    const mappedField = fieldMap[field] || field;

    // 타입 안전한 방식으로 direction 처리
    let validDirection: 'asc' | 'desc' = 'desc';
    if (direction === 'asc') {
      validDirection = 'asc';
    }

    return {
      field: mappedField,
      direction: validDirection,
    };
  });
};
