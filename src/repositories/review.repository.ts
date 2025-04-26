import { prisma } from '../lib/prisma';
import { Prisma } from '@prisma/client';

export const reviewRepository = {
  /**
   * 리뷰 목록 조회
   */
  async findAll(skip: number, take: number) {
    return prisma.review.findMany({
      include: {
        user: true,
        product: true
      },
      orderBy: {
        createdAt: 'desc'
      },
      take,
      skip,
    });
  },

  /**
   * 특정 상품의 리뷰 조회
   */
  async findByProductId(productId: number, skip: number, take: number) {
    return prisma.review.findMany({
      where: {
        productId
      },
      include: {
        user: true
      },
      orderBy: {
        createdAt: 'desc'
      },
      take,
      skip,
    });
  },

  /**
   * 특정 상품의 리뷰 개수 조회
   */
  async countByProductId(productId: number) {
    return prisma.review.count({
      where: {
        productId
      }
    });
  },

  /**
   * 리뷰 생성
   */
  async create(data: Prisma.ReviewCreateInput) {
    return prisma.review.create({
      data,
      include: {
        user: true
      }
    });
  },

  /**
   * 리뷰 수정
   */
  async update(id: number, data: Prisma.ReviewUpdateInput) {
    return prisma.review.update({
      where: { id },
      data,
      include: {
        user: true
      }
    });
  },

  /**
   * 리뷰 삭제
   */
  async delete(id: number) {
    return prisma.review.delete({
      where: { id }
    });
  },

  /**
   * 리뷰 조회
   */
  async findById(id: number) {
    return prisma.review.findUnique({
      where: { id },
      include: {
        user: true,
        product: true
      }
    });
  }
}; 