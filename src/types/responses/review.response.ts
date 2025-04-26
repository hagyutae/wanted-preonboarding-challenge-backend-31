// 리뷰 API 응답 타입 정의
import { PaginatedData } from '../response';

/**
 * 리뷰 작성자 정보 응답 타입
 */
export interface ReviewUserResponse {
  id: number;
  name: string;
  avatar_url?: string;
}

/**
 * 리뷰 기본 정보 응답 타입
 */
export interface ReviewResponse {
  id: number;
  user: ReviewUserResponse;
  rating: number;
  title?: string;
  content?: string;
  created_at: string;
  updated_at: string;
  verified_purchase: boolean;
  helpful_votes: number;
}

/**
 * 리뷰 업데이트 응답 타입
 */
export interface ReviewUpdateResponse {
  id: number;
  rating: number;
  title?: string;
  content?: string;
  updated_at: string;
}

/**
 * 평점 분포 응답 타입
 */
export interface RatingDistributionResponse {
  '5': number;
  '4': number;
  '3': number;
  '2': number;
  '1': number;
}

/**
 * 리뷰 요약 정보 응답 타입
 */
export interface ReviewSummaryResponse {
  average_rating: number;
  total_count: number;
  distribution: RatingDistributionResponse;
}

/**
 * 리뷰 목록 응답 타입
 */
export interface ReviewListResponse {
  items: ReviewResponse[];
  summary: ReviewSummaryResponse;
  pagination: {
    total_items: number;
    total_pages: number;
    current_page: number;
    per_page: number;
  };
} 