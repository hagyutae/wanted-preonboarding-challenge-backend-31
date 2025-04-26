import { Prisma } from '@prisma/client';
import { reviewRepository } from '../repositories/review.repository';
import { productRepository } from '../repositories/product.repository';
import { PaginationParams } from '../utils/pagination';
import { prisma } from '../lib/prisma';
import { ReviewResponse, ReviewUserResponse } from '../types';

interface ReviewsResult {
  reviews: ReviewResponse[];
  total: number;
  page: number;
  perPage: number;
  totalPages: number;
}

// DB 모델을 API 응답으로 변환하는 헬퍼 함수
function mapReviewToApiResponse(review: any): ReviewResponse {
  return {
    id: review.id,
    user: {
      id: review.user.id,
      name: review.user.name || '사용자',
      avatar_url: review.user.avatarUrl || undefined
    },
    rating: review.rating,
    title: review.title || undefined,
    content: review.content || undefined,
    created_at: review.createdAt.toISOString(),
    updated_at: review.updatedAt.toISOString(),
    verified_purchase: review.verifiedPurchase || false,
    helpful_votes: review.helpfulVotes || 0
  };
}

export const reviewService = {
  /**
   * 모든 리뷰 조회
   */
  async getAllReviews({ page, perPage }: PaginationParams): Promise<ReviewsResult> {
    const skip = (page - 1) * perPage;
    const take = perPage;

    const total = await prisma.review.count();
    const dbReviews = await reviewRepository.findAll(skip, take);
    
    // DB 모델을 API 응답 형식으로 변환
    const reviews = dbReviews.map(mapReviewToApiResponse);

    return {
      reviews,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage)
    };
  },

  /**
   * 특정 상품의 리뷰 조회
   */
  async getProductReviews(productId: number, { page, perPage }: PaginationParams): Promise<ReviewsResult> {
    // 상품 존재 여부 확인
    const product = await productRepository.findById(productId);
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const skip = (page - 1) * perPage;
    const take = perPage;

    const [dbReviews, total] = await Promise.all([
      reviewRepository.findByProductId(productId, skip, take),
      reviewRepository.countByProductId(productId)
    ]);
    
    // DB 모델을 API 응답 형식으로 변환
    const reviews = dbReviews.map(mapReviewToApiResponse);

    return {
      reviews,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage)
    };
  },

  /**
   * 리뷰 생성
   */
  async createReview(productId: number, userId: number, reviewData: Omit<Prisma.ReviewCreateInput, 'product' | 'user'>): Promise<ReviewResponse> {
    // 상품 존재 여부 확인
    const product = await productRepository.findById(productId);
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const data: Prisma.ReviewCreateInput = {
      ...reviewData,
      product: {
        connect: { id: productId }
      },
      user: {
        connect: { id: userId }
      }
    };

    const newReview = await reviewRepository.create(data);
    
    // 사용자 정보 조회를 위한 추가 쿼리
    const user = await prisma.user.findUnique({ where: { id: userId } });
    
    // API 응답 형식으로 변환
    return {
      id: newReview.id,
      user: {
        id: userId,
        name: user?.name || '사용자',
        avatar_url: user?.avatarUrl || undefined
      },
      rating: newReview.rating,
      title: newReview.title || undefined,
      content: newReview.content || undefined,
      created_at: newReview.createdAt.toISOString(),
      updated_at: newReview.updatedAt.toISOString(),
      verified_purchase: newReview.verifiedPurchase || false,
      helpful_votes: newReview.helpfulVotes || 0
    };
  },

  /**
   * 리뷰 수정
   */
  async updateReview(reviewId: number, userId: number, reviewData: Prisma.ReviewUpdateInput): Promise<ReviewResponse> {
    const review = await reviewRepository.findById(reviewId);
    
    if (!review) {
      throw new Error('리뷰를 찾을 수 없습니다.');
    }

    if (review.userId !== userId) {
      throw new Error('리뷰를 수정할 권한이 없습니다.');
    }

    const updatedReview = await reviewRepository.update(reviewId, reviewData);
    
    // 사용자 정보 조회를 위한 추가 쿼리
    const user = await prisma.user.findUnique({ where: { id: userId } });
    
    // API 응답 형식으로 변환
    return {
      id: updatedReview.id,
      user: {
        id: userId,
        name: user?.name || '사용자',
        avatar_url: user?.avatarUrl || undefined
      },
      rating: updatedReview.rating,
      title: updatedReview.title || undefined,
      content: updatedReview.content || undefined,
      created_at: updatedReview.createdAt.toISOString(),
      updated_at: updatedReview.updatedAt.toISOString(),
      verified_purchase: updatedReview.verifiedPurchase || false,
      helpful_votes: updatedReview.helpfulVotes || 0
    };
  },

  /**
   * 리뷰 삭제
   */
  async deleteReview(reviewId: number, userId: number): Promise<void> {
    const review = await reviewRepository.findById(reviewId);
    
    if (!review) {
      throw new Error('리뷰를 찾을 수 없습니다.');
    }

    if (review.userId !== userId) {
      throw new Error('리뷰를 삭제할 권한이 없습니다.');
    }

    await reviewRepository.delete(reviewId);
  },

  /**
   * 리뷰 상세 조회
   */
  async getReviewById(reviewId: number): Promise<ReviewResponse> {
    const review = await reviewRepository.findById(reviewId);
    
    if (!review) {
      throw new Error('리뷰를 찾을 수 없습니다.');
    }
    
    // API 응답 형식으로 변환
    return mapReviewToApiResponse(review);
  }
}; 