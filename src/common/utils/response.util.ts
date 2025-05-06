// src/common/utils/response.utils.ts
import {
  ApiSuccessResponse,
  ApiPaginatedResponse,
  ApiErrorResponse,
  PaginatedData,
  ErrorCode,
  ErrorInfo,
  PaginationParams,
  PaginationInfo,
} from '../types/response.type';

/**
 * 성공 응답 생성 유틸리티
 * @param data 응답 데이터
 * @param message 성공 메시지
 * @returns 표준화된 성공 응답 객체
 */
export function createSuccessResponse<T>(
  data: T,
  message = '요청이 성공적으로 처리되었습니다.',
): ApiSuccessResponse<T> {
  return {
    success: true,
    data,
    message,
  };
}

/**
 * 페이지네이션 정보 생성 유틸리티
 * @param totalItems 전체 아이템 수
 * @param params 페이지네이션 파라미터
 * @returns 페이지네이션 정보 객체
 */
export function createPaginationInfo(
  totalItems: number,
  params: PaginationParams,
): PaginationInfo {
  const perPage = Number(params.per_page) || 10;
  const currentPage = Number(params.page) || 1;
  const totalPages = Math.ceil(totalItems / perPage);

  return {
    total_items: totalItems,
    total_pages: totalPages,
    current_page: currentPage,
    per_page: perPage,
  };
}

export function createPaginatedData<T>(
  items: T[],
  totalItems: number,
  params: PaginationParams,
): PaginatedData<T> {
  return { items, pagination: createPaginationInfo(totalItems, params) };
}

/**
 * 페이지네이션 응답 생성 유틸리티
 * @param items 페이지네이션된 데이터 아이템 배열
 * @param totalItems 전체 아이템 수
 * @param params 페이지네이션 파라미터
 * @param message 성공 메시지
 * @returns 표준화된 페이지네이션 응답 객체
 */
export function createPaginatedResponse<T>(
  items: T[],
  totalItems: number,
  params: PaginationParams,
  message = '요청이 성공적으로 처리되었습니다.',
): ApiPaginatedResponse<T> {
  const paginatedData = createPaginatedData(items, totalItems, params);

  return createSuccessResponse(paginatedData, message);
}

/**
 * 에러 응답 생성 유틸리티
 * @param code 에러 코드
 * @param message 에러 메시지
 * @param details 추가 에러 상세 정보
 * @returns 표준화된 에러 응답 객체
 */
export function createErrorResponse(
  code: ErrorCode | string,
  message: string,
  details?: Record<string, any>,
): ApiErrorResponse {
  const errorInfo: ErrorInfo = {
    code,
    message,
  };

  if (details) {
    errorInfo.details = details;
  }

  return {
    success: false,
    error: errorInfo,
  };
}
