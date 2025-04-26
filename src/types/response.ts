// API 응답 타입 정의

/**
 * 기본 성공 응답 인터페이스
 */
export interface SuccessResponse<T> {
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
 * 페이지네이션 포함 응답 데이터 인터페이스
 */
export interface PaginatedData<T> {
  items: T[];
  pagination: PaginationInfo;
}

/**
 * 페이지네이션 포함 성공 응답 인터페이스
 */
export interface PaginatedResponse<T> extends SuccessResponse<PaginatedData<T>> {}

/**
 * 에러 상세 정보 인터페이스 (선택적)
 */
export interface ErrorDetails {
  [key: string]: string | number | boolean | object;
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
export interface ErrorResponse {
  success: false;
  error: ErrorInfo;
}

/**
 * 표준 API 응답 (성공 또는 실패)
 */
export type ApiResponse<T> = SuccessResponse<T> | ErrorResponse; 