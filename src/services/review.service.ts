import { Prisma } from '@prisma/client';
import { reviewRepository } from '../repositories/review.repository';
import { productRepository } from '../repositories/product.repository';
import { PaginationParams } from '../utils/pagination';
import { prisma } from '../lib/prisma';

export const reviewService = {
  /**
   * 모든 리뷰 조회
   */
  async getAllReviews({ page, perPage }: PaginationParams) {
    const skip = (page - 1) * perPage;
    const take = perPage;

    const total = await prisma.review.count();
    const reviews = await reviewRepository.findAll(skip, take);

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
  async getProductReviews(productId: number, { page, perPage }: PaginationParams) {
    // 상품 존재 여부 확인
    const product = await productRepository.findById(productId);
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const skip = (page - 1) * perPage;
    const take = perPage;

    const [reviews, total] = await Promise.all([
      reviewRepository.findByProductId(productId, skip, take),
      reviewRepository.countByProductId(productId)
    ]);

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
  async createReview(productId: number, userId: number, reviewData: Omit<Prisma.ReviewCreateInput, 'product' | 'user'>) {
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

    return reviewRepository.create(data);
  },

  /**
   * 리뷰 수정
   */
  async updateReview(reviewId: number, userId: number, reviewData: Prisma.ReviewUpdateInput) {
    const review = await reviewRepository.findById(reviewId);
    
    if (!review) {
      throw new Error('리뷰를 찾을 수 없습니다.');
    }

    if (review.userId !== userId) {
      throw new Error('리뷰를 수정할 권한이 없습니다.');
    }

    return reviewRepository.update(reviewId, reviewData);
  },

  /**
   * 리뷰 삭제
   */
  async deleteReview(reviewId: number, userId: number) {
    const review = await reviewRepository.findById(reviewId);
    
    if (!review) {
      throw new Error('리뷰를 찾을 수 없습니다.');
    }

    if (review.userId !== userId) {
      throw new Error('리뷰를 삭제할 권한이 없습니다.');
    }

    return reviewRepository.delete(reviewId);
  },

  /**
   * 리뷰 상세 조회
   */
  async getReviewById(reviewId: number) {
    const review = await reviewRepository.findById(reviewId);
    
    if (!review) {
      throw new Error('리뷰를 찾을 수 없습니다.');
    }

    return review;
  }
}; 