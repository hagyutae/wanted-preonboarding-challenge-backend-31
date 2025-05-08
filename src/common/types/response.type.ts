/**
 * 기본 성공 응답 인터페이스
 */
export interface ApiSuccessResponse<T> {
  success: true;
  data: T;
  message: string;
}

/**
 * 페이지네이션 정보 인터페이스
 */
export interface PaginationInfo {
  total_items: number;
  total_pages: number;
  current_page: number;
  per_page: number;
}

/**
 * 페이지네이션이 포함된 데이터 인터페이스
 */
export interface PaginatedData<T> {
  items: T[];
  pagination: PaginationInfo;
}

/**
 * 페이지네이션 응답 타입
 */
export type ApiPaginatedResponse<T> = ApiSuccessResponse<PaginatedData<T>>;

/**
 * 에러 상세 정보 인터페이스
 */
export interface ErrorDetails {
  [key: string]: any;
}

/**
 * 에러 정보 인터페이스
 */
export interface ErrorInfo {
  code: string;
  message: string;
  details?: ErrorDetails;
}

/**
 * 에러 응답 인터페이스
 */
export interface ApiErrorResponse {
  success: false;
  error: ErrorInfo;
}

/**
 * API 응답 통합 타입(성공 또는 실패)
 */
export type ApiResponse<T = any> = ApiSuccessResponse<T> | ApiErrorResponse;

/**
 * 에러 코드 열거형
 */
export enum ErrorCode {
  INVALID_INPUT = 'INVALID_INPUT',
  RESOURCE_NOT_FOUND = 'RESOURCE_NOT_FOUND',
  UNAUTHORIZED = 'UNAUTHORIZED',
  FORBIDDEN = 'FORBIDDEN',
  CONFLICT = 'CONFLICT',
  INTERNAL_ERROR = 'INTERNAL_ERROR',
}

/**
 * HTTP 상태 코드와 에러 코드 매핑
 */
export const ERROR_CODE_TO_HTTP_STATUS: Record<ErrorCode, number> = {
  [ErrorCode.INVALID_INPUT]: 400,
  [ErrorCode.RESOURCE_NOT_FOUND]: 404,
  [ErrorCode.UNAUTHORIZED]: 401,
  [ErrorCode.FORBIDDEN]: 403,
  [ErrorCode.CONFLICT]: 409,
  [ErrorCode.INTERNAL_ERROR]: 500,
};

/**
 * 페이지네이션 요청 파라미터 인터페이스
 */
export interface PaginationParams {
  page?: number;
  per_page?: number;
}
