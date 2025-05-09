import { PaginationInfo, SuccessResponse, ErrorResponse, ErrorInfo, PaginatedData } from '../types';

/**
 * 성공 응답을 생성하는 함수
 * @param data 응답 데이터
 * @param message 응답 메시지
 */
export function createSuccessResponse<T>(data: T, message: string): SuccessResponse<T> {
  return {
    success: true,
    data,
    message
  };
}

/**
 * 페이지네이션이 포함된 성공 응답을 생성하는 함수
 * @param items 아이템 목록
 * @param total 전체 아이템 수
 * @param page 현재 페이지
 * @param perPage 페이지당 아이템 수
 */
export function createPaginatedResponse<T>(
  items: T[],
  total: number,
  page: number,
  perPage: number,
  message: string
): SuccessResponse<PaginatedData<T>> {
  return {
    success: true,
    data: {
      items,
      pagination: {
        total_items: total,
        total_pages: Math.ceil(total / perPage),
        current_page: page,
        per_page: perPage
      }
    },
    message
  };
}

/**
 * 에러 응답을 생성하는 함수
 * @param errorInfo 에러 정보
 */
export function createErrorResponse(errorInfo: ErrorInfo): ErrorResponse {
  return {
    success: false,
    error: errorInfo
  };
}

/**
 * 공통 에러 코드 상수
 */
export const ErrorCode = {
  INVALID_INPUT: 'INVALID_INPUT',
  RESOURCE_NOT_FOUND: 'RESOURCE_NOT_FOUND',
  UNAUTHORIZED: 'UNAUTHORIZED',
  FORBIDDEN: 'FORBIDDEN',
  CONFLICT: 'CONFLICT',
  INTERNAL_ERROR: 'INTERNAL_ERROR'
}; 