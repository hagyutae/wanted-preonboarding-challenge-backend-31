import { prisma } from '../lib/prisma';
import { Prisma } from '@prisma/client';

export const categoryRepository = {
  /**
   * 모든 카테고리 조회 (계층 구조 포함)
   * @param level - 필터링할 카테고리 레벨 (선택 사항)
   * @returns 계층 구조를 포함한 카테고리 목록
   */
  async findAll(level?: number) {
    const where: Prisma.CategoryWhereInput = {};
    if (level) {
      where.level = level;
    }

    // 레벨이 지정된 경우 해당 레벨만, 그렇지 않으면 모든 카테고리를 계층 구조로 가져옴
    if (level) {
      return prisma.category.findMany({
        where,
        include: {
          parent: true,
          children: {
            include: {
              children: true,
            },
          },
        },
        orderBy: {
          id: 'asc',
        },
      });
    } else {
      // 레벨이 지정되지 않은 경우 최상위 카테고리부터 모든 하위 항목을 포함하여 가져옴
      return prisma.category.findMany({
        where: {
          level: 1,
        },
        include: {
          children: {
            include: {
              children: true,
            },
          },
        },
        orderBy: {
          id: 'asc',
        },
      });
    }
  },

  /**
   * 모든 최상위(레벨 1) 카테고리와 하위 카테고리를 조회
   * @returns 최상위 카테고리 및 연결된 하위 카테고리 목록
   */
  async findAllRoot() {
    return prisma.category.findMany({
      where: {
        level: 1,
      },
      include: {
        children: {
          include: {
            children: true,
          },
        },
      },
      orderBy: {
        id: 'asc',
      },
    });
  },

  /**
   * ID로 카테고리 조회 (하위 카테고리 포함)
   * @param id - 조회할 카테고리 ID
   * @returns 카테고리 정보와 하위 카테고리 구조
   */
  async findById(id: number) {
    return prisma.category.findUnique({
      where: { id },
      include: {
        parent: true,
        children: {
          include: {
            children: true,
          },
        },
      },
    });
  },

  /**
   * 특정 카테고리에 속한 상품 개수 조회
   * @param categoryIds - 카테고리 ID 배열
   * @returns 해당 카테고리들에 속한 상품 개수
   */
  async countProducts(categoryIds: number[]) {
    return prisma.product.count({
      where: {
        categories: {
          some: {
            categoryId: {
              in: categoryIds,
            },
          },
        },
      },
    });
  },

  /**
   * 특정 카테고리에 속한 상품 목록 조회
   * @param categoryIds - 카테고리 ID 배열
   * @param skip - 건너뛸 상품 수 (페이지네이션)
   * @param take - 가져올 상품 수 (페이지네이션)
   * @param orderBy - 정렬 조건
   * @returns 카테고리에 속한 상품 목록
   */
  async findProductsByCategoryIds(
    categoryIds: number[],
    skip: number,
    take: number,
    orderBy: Prisma.ProductOrderByWithRelationInput,
  ) {
    return prisma.product.findMany({
      where: {
        categories: {
          some: {
            categoryId: {
              in: categoryIds,
            },
          },
        },
      },
      include: {
        brand: true,
        seller: true,
        images: true,
        price: true,
        optionGroups: {
          include: {
            options: {
              select: {
                stock: true,
              },
            },
          },
        },
      },
      orderBy,
      skip,
      take,
    });
  },
};
