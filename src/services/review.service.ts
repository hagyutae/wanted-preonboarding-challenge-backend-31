import { Prisma } from '@prisma/client';
import { reviewRepository } from '../repositories/review.repository';
import { productRepository } from '../repositories/product.repository';
import { PaginationParams } from '../utils/pagination';
import { prisma } from '../lib/prisma';
import { ReviewResponse, ReviewUserResponse } from '../types';
import { UpdateReviewRequest } from '../types/requests/review.request';
import { ReviewListResponse, ReviewUpdateResponse } from '../types/responses/review.response';
import { mapReviewToApiResponse, ReviewWithRelations } from '../types/mappers/review.mapper';

// 리뷰 응답 변환을 위한 타입 가드
function isReviewWithRelations(review: unknown): review is ReviewWithRelations {
  return (
    review !== null &&
    typeof review === 'object' &&
    'user' in review &&
    review.user !== null &&
    typeof review.user === 'object'
  );
}

// 리뷰 배열 변환 헬퍼 함수
function mapReviewsToResponse(reviews: unknown[]): ReviewResponse[] {
  return reviews.filter(isReviewWithRelations).map((review) => mapReviewToApiResponse(review));
}

interface ReviewsResult {
  reviews: ReviewResponse[];
  total: number;
  page: number;
  perPage: number;
  totalPages: number;
}

export const reviewService = {
  /**
   * 리뷰 목록 조회
   */
  async getReviews(
    productId: number,
    { page, perPage }: PaginationParams,
  ): Promise<ReviewListResponse> {
    // 리뷰 데이터 조회
    const skip = (page - 1) * perPage;
    const reviews = await reviewRepository.findByProductId(productId, skip, perPage);
    const totalCount = await reviewRepository.countByProductId(productId);

    // 평점 집계
    const allReviews = await reviewRepository.findByProductId(productId, 0, 1000);
    const averageRating =
      allReviews.length > 0
        ? Math.round((allReviews.reduce((sum, r) => sum + r.rating, 0) / allReviews.length) * 10) /
          10
        : 0;

    // 평점 분포 계산
    const distribution: Record<'1' | '2' | '3' | '4' | '5', number> = {
      '5': 0,
      '4': 0,
      '3': 0,
      '2': 0,
      '1': 0,
    };

    allReviews.forEach((review) => {
      const ratingKey = review.rating.toString();
      if (
        ratingKey === '1' ||
        ratingKey === '2' ||
        ratingKey === '3' ||
        ratingKey === '4' ||
        ratingKey === '5'
      ) {
        distribution[ratingKey]++;
      }
    });

    return {
      items: mapReviewsToResponse(reviews),
      summary: {
        average_rating: averageRating,
        total_count: allReviews.length,
        distribution,
      },
      pagination: {
        total_items: totalCount,
        total_pages: Math.ceil(totalCount / perPage),
        current_page: page,
        per_page: perPage,
      },
    };
  },

  /**
   * 리뷰 상세 조회
   */
  async getReviewById(reviewId: number): Promise<ReviewResponse> {
    const review = await reviewRepository.findById(reviewId);

    if (!review) {
      throw new Error(`리뷰 ID ${reviewId}를 찾을 수 없습니다`);
    }

    if (!isReviewWithRelations(review)) {
      throw new Error(`리뷰 ID ${reviewId}에 관련 정보가 없습니다`);
    }

    return mapReviewToApiResponse(review);
  },

  /**
   * 리뷰 등록
   */
  async createReview(
    productId: number,
    userId: number,
    data: UpdateReviewRequest,
  ): Promise<ReviewResponse> {
    const reviewData: Prisma.ReviewCreateInput = {
      rating: data.rating,
      title: data.title || null,
      content: data.content || null,
      product: {
        connect: { id: productId },
      },
      user: {
        connect: { id: userId },
      },
      verifiedPurchase: false,
    };

    const review = await reviewRepository.create(reviewData);

    // 생성된 리뷰 조회
    const createdReview = await reviewRepository.findById(review.id);
    if (!createdReview) {
      throw new Error(`생성된 리뷰 ID ${review.id}를 찾을 수 없습니다`);
    }

    if (!isReviewWithRelations(createdReview)) {
      throw new Error(`리뷰 ID ${review.id}에 관련 정보가 없습니다`);
    }

    return mapReviewToApiResponse(createdReview);
  },

  /**
   * 리뷰 수정
   */
  async updateReview(
    reviewId: number,
    userId: number,
    data: Partial<UpdateReviewRequest>,
  ): Promise<ReviewUpdateResponse> {
    // 리뷰 존재 확인
    const existingReview = await reviewRepository.findById(reviewId);

    if (!existingReview) {
      throw new Error(`리뷰 ID ${reviewId}를 찾을 수 없습니다`);
    }

    // 작성자 확인
    if (existingReview.userId !== userId) {
      throw new Error('리뷰 수정 권한이 없습니다');
    }

    // 리뷰 수정
    const updatedReview = await reviewRepository.update(reviewId, {
      rating: data.rating ?? undefined,
      title: data.title ?? undefined,
      content: data.content ?? undefined,
    });

    return {
      id: updatedReview.id,
      rating: updatedReview.rating,
      title: updatedReview.title ?? undefined,
      content: updatedReview.content ?? undefined,
      updated_at: updatedReview.updatedAt.toISOString(),
    };
  },

  /**
   * 리뷰 삭제
   */
  async deleteReview(reviewId: number, userId: number): Promise<void> {
    // 리뷰 존재 확인
    const existingReview = await reviewRepository.findById(reviewId);

    if (!existingReview) {
      throw new Error(`리뷰 ID ${reviewId}를 찾을 수 없습니다`);
    }

    // 작성자 확인
    if (existingReview.userId !== userId) {
      throw new Error('리뷰 삭제 권한이 없습니다');
    }

    // 리뷰 삭제
    await reviewRepository.delete(reviewId);
  },

  /**
   * 상품 리뷰 조회
   */
  async getProductReviews(
    productId: number,
    { page, perPage }: PaginationParams,
    rating?: number,
  ): Promise<ReviewListResponse> {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    // 리뷰 데이터 조회
    const skip = (page - 1) * perPage;
    const reviews = await reviewRepository.findByProductId(productId, skip, perPage, rating);
    const totalCount = await reviewRepository.countByProductId(productId, rating);

    // 평점 집계
    const allReviews = await reviewRepository.findByProductId(productId, 0, 1000);
    const averageRating =
      allReviews.length > 0
        ? Math.round((allReviews.reduce((sum, r) => sum + r.rating, 0) / allReviews.length) * 10) /
          10
        : 0;

    // 평점 분포 계산
    const distribution: Record<'1' | '2' | '3' | '4' | '5', number> = {
      '5': 0,
      '4': 0,
      '3': 0,
      '2': 0,
      '1': 0,
    };

    allReviews.forEach((review) => {
      const ratingKey = review.rating.toString();
      if (
        ratingKey === '1' ||
        ratingKey === '2' ||
        ratingKey === '3' ||
        ratingKey === '4' ||
        ratingKey === '5'
      ) {
        distribution[ratingKey]++;
      }
    });

    return {
      items: mapReviewsToResponse(reviews),
      summary: {
        average_rating: averageRating,
        total_count: allReviews.length,
        distribution,
      },
      pagination: {
        total_items: totalCount,
        total_pages: Math.ceil(totalCount / perPage),
        current_page: page,
        per_page: perPage,
      },
    };
  },
};
