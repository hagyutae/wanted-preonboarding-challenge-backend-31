import { prisma } from '../lib/prisma';
import { Prisma } from '@prisma/client';

export const categoryRepository = {
  /**
   * 모든 최상위 카테고리와 하위 카테고리를 조회
   */
  async findAllRoot() {
    return prisma.category.findMany({
      where: {
        level: 1
      },
      include: {
        children: {
          include: {
            children: true
          }
        }
      },
      orderBy: {
        id: 'asc'
      }
    });
  },

  /**
   * ID로 카테고리 조회 (하위 카테고리 포함)
   */
  async findById(id: number) {
    return prisma.category.findUnique({
      where: { id },
      include: {
        children: {
          include: {
            children: true
          }
        }
      }
    });
  },

  /**
   * 특정 카테고리에 속한 상품 카운트
   */
  async countProducts(categoryIds: number[]) {
    return prisma.product.count({
      where: {
        categories: {
          some: {
            categoryId: {
              in: categoryIds
            }
          }
        }
      }
    });
  },

  /**
   * 특정 카테고리에 속한 상품 목록 조회
   */
  async findProductsByCategoryIds(
    categoryIds: number[],
    skip: number,
    take: number,
    orderBy: Prisma.ProductOrderByWithRelationInput
  ) {
    return prisma.product.findMany({
      where: {
        categories: {
          some: {
            categoryId: {
              in: categoryIds
            }
          }
        }
      },
      include: {
        brand: true,
        images: {
          where: { isPrimary: true },
          take: 1
        },
        price: true
      },
      orderBy,
      skip,
      take
    });
  }
}; 