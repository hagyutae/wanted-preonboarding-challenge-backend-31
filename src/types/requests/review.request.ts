// 리뷰 API 요청 타입 정의

/**
 * 리뷰 목록 조회 요청 파라미터
 */
export interface GetReviewsRequest {
  page?: number;
  perPage?: number;
  sort?: string;
  rating?: number;
}

/**
 * 리뷰 수정 요청 본문
 */
export interface UpdateReviewRequest {
  rating: number;
  title?: string;
  content?: string;
} 