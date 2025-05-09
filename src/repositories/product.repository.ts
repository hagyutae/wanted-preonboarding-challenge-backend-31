import { prisma } from '../lib/prisma';
import { Prisma } from '@prisma/client';
import { ProductListItemResponse } from '../types/responses/product.response';
import { ProductFilterParams, ProductSortParams } from '../types/product.types';
import { ProductWithRelations, mapProductToListItem } from '../types/mappers/product.mapper';

/**
 * ProductWithRelations 타입인지 확인하는 타입 가드 함수
 * @param product - 확인할 객체
 * @returns 객체가 ProductWithRelations 타입인지 여부
 */
function isProductWithRelations(product: unknown): product is ProductWithRelations {
  if (!product || typeof product !== 'object') return false;

  const p = product;
  return (
    'id' in p &&
    typeof p.id === 'number' &&
    'name' in p &&
    typeof p.name === 'string' &&
    'slug' in p &&
    typeof p.slug === 'string' &&
    'images' in p &&
    Array.isArray(p.images)
  );
}

/**
 * 상품 배열을 API 응답 형식으로 안전하게 변환
 * @param products - 변환할 상품 객체 배열
 * @returns 변환된 상품 응답 객체 배열
 */
function safeMapToProductResponse(products: unknown[]): ProductListItemResponse[] {
  return products.filter(isProductWithRelations).map((product) => mapProductToListItem(product));
}

export const productRepository = {
  /**
   * 상품 목록 조회
   * @param skip - 건너뛸 상품 수
   * @param take - 가져올 상품 수
   * @param filters - 필터링 조건 (선택 사항)
   * @param sorts - 정렬 조건 (선택 사항)
   * @returns 상품 목록
   */
  async findAll(
    skip: number,
    take: number,
    filters?: ProductFilterParams,
    sorts?: ProductSortParams[],
  ): Promise<ProductListItemResponse[]> {
    // 필터링 조건 구성
    const where: Prisma.ProductWhereInput = {};

    if (filters) {
      // 상태 필터
      if (filters.status) {
        where.status = filters.status;
      }

      // 판매자 필터
      if (filters.seller) {
        where.sellerId = filters.seller;
      }

      // 브랜드 필터
      if (filters.brand) {
        where.brandId = filters.brand;
      }

      // 카테고리 필터
      if (filters.category && Array.isArray(filters.category) && filters.category.length > 0) {
        where.categories = {
          some: {
            categoryId: {
              in: filters.category,
            },
          },
        };
      } else if (filters.category && !Array.isArray(filters.category)) {
        where.categories = {
          some: {
            categoryId: filters.category,
          },
        };
      }

      // 태그 필터
      if (filters.tags && filters.tags.length > 0) {
        where.tags = {
          some: {
            tagId: {
              in: filters.tags,
            },
          },
        };
      }

      // 가격 범위 필터
      if (filters.minPrice || filters.maxPrice) {
        where.price = {};

        if (filters.minPrice) {
          where.price.basePrice = { gte: filters.minPrice };
        }

        if (filters.maxPrice) {
          where.price.basePrice = filters.minPrice
            ? { gte: filters.minPrice, lte: filters.maxPrice }
            : { lte: filters.maxPrice };
        }
      }

      // 등록일 범위 필터
      if (filters.createdAtStart || filters.createdAtEnd) {
        where.createdAt = {};

        if (filters.createdAtStart) {
          where.createdAt = { gte: filters.createdAtStart };
        }

        if (filters.createdAtEnd) {
          where.createdAt = filters.createdAtStart
            ? { gte: filters.createdAtStart, lte: filters.createdAtEnd }
            : { lte: filters.createdAtEnd };
        }
      }

      // 재고 유무 필터
      if (filters.inStock !== undefined) {
        where.optionGroups = {
          some: {
            options: {
              some: {
                stock: filters.inStock ? { gt: 0 } : { lte: 0 },
              },
            },
          },
        };
      }

      // 검색어 필터
      if (filters.search) {
        where.OR = [
          { name: { contains: filters.search, mode: 'insensitive' } },
          { shortDescription: { contains: filters.search, mode: 'insensitive' } },
          { fullDescription: { contains: filters.search, mode: 'insensitive' } },
          { brand: { name: { contains: filters.search, mode: 'insensitive' } } },
          { tags: { some: { tag: { name: { contains: filters.search, mode: 'insensitive' } } } } },
          {
            categories: {
              some: { category: { name: { contains: filters.search, mode: 'insensitive' } } },
            },
          },
        ];
      }
    }

    // 정렬 조건 구성
    const orderBy: Prisma.ProductOrderByWithRelationInput[] = [];

    if (sorts && sorts.length > 0) {
      sorts.forEach((sort) => {
        // 특수 케이스 처리
        if (sort.field === 'price.basePrice') {
          orderBy.push({ price: { basePrice: sort.direction } });
        } else if (sort.field === 'price.salePrice') {
          orderBy.push({ price: { salePrice: sort.direction } });
        } else if (sort.field === 'rating') {
          // 평점 정렬은 리뷰 평균 값으로 처리 필요함
          // 임시로 생성일 기준 정렬
          orderBy.push({ createdAt: sort.direction });
        } else if (sort.field === 'createdAt') {
          orderBy.push({ createdAt: sort.direction });
        } else if (sort.field === 'updatedAt') {
          orderBy.push({ updatedAt: sort.direction });
        } else if (sort.field === 'name') {
          orderBy.push({ name: sort.direction });
        }
      });
    } else {
      // 기본 정렬: 최신순
      orderBy.push({ createdAt: 'desc' });
    }

    // 쿼리 실행
    const products = await prisma.product.findMany({
      where,
      include: {
        brand: true,
        seller: true,
        price: true,
        images: {
          orderBy: {
            displayOrder: 'asc',
          },
        },
        optionGroups: {
          include: {
            options: {
              orderBy: {
                displayOrder: 'asc',
              },
            },
          },
          orderBy: {
            displayOrder: 'asc',
          },
        },
        reviews: {
          select: {
            rating: true,
          },
        },
        categories: {
          include: {
            category: {
              include: {
                parent: true,
              },
            },
          },
        },
        tags: {
          include: {
            tag: true,
          },
        },
      },
      orderBy,
      take,
      skip,
    });

    // DB 모델에서 API 응답 형식으로 변환
    return safeMapToProductResponse(products);
  },

  /**
   * 상품 전체 개수 조회 (필터링 적용)
   */
  async count(filters?: ProductFilterParams): Promise<number> {
    // 필터링 조건 구성
    const where: Prisma.ProductWhereInput = {};

    if (filters) {
      // 상태 필터
      if (filters.status) {
        where.status = filters.status;
      }

      // 판매자 필터
      if (filters.seller) {
        where.sellerId = filters.seller;
      }

      // 브랜드 필터
      if (filters.brand) {
        where.brandId = filters.brand;
      }

      // 카테고리 필터
      if (filters.category && Array.isArray(filters.category) && filters.category.length > 0) {
        where.categories = {
          some: {
            categoryId: {
              in: filters.category,
            },
          },
        };
      } else if (filters.category && !Array.isArray(filters.category)) {
        where.categories = {
          some: {
            categoryId: filters.category,
          },
        };
      }

      // 태그 필터
      if (filters.tags && filters.tags.length > 0) {
        where.tags = {
          some: {
            tagId: {
              in: filters.tags,
            },
          },
        };
      }

      // 가격 범위 필터
      if (filters.minPrice || filters.maxPrice) {
        where.price = {};

        if (filters.minPrice) {
          where.price.basePrice = { gte: filters.minPrice };
        }

        if (filters.maxPrice) {
          where.price.basePrice = filters.minPrice
            ? { gte: filters.minPrice, lte: filters.maxPrice }
            : { lte: filters.maxPrice };
        }
      }

      // 등록일 범위 필터
      if (filters.createdAtStart || filters.createdAtEnd) {
        where.createdAt = {};

        if (filters.createdAtStart) {
          where.createdAt = { gte: filters.createdAtStart };
        }

        if (filters.createdAtEnd) {
          where.createdAt = filters.createdAtStart
            ? { gte: filters.createdAtStart, lte: filters.createdAtEnd }
            : { lte: filters.createdAtEnd };
        }
      }

      // 재고 유무 필터
      if (filters.inStock !== undefined) {
        where.optionGroups = {
          some: {
            options: {
              some: {
                stock: filters.inStock ? { gt: 0 } : { lte: 0 },
              },
            },
          },
        };
      }

      // 검색어 필터
      if (filters.search) {
        where.OR = [
          { name: { contains: filters.search, mode: 'insensitive' } },
          { shortDescription: { contains: filters.search, mode: 'insensitive' } },
          { fullDescription: { contains: filters.search, mode: 'insensitive' } },
          { brand: { name: { contains: filters.search, mode: 'insensitive' } } },
          { tags: { some: { tag: { name: { contains: filters.search, mode: 'insensitive' } } } } },
          {
            categories: {
              some: { category: { name: { contains: filters.search, mode: 'insensitive' } } },
            },
          },
        ];
      }
    }

    return prisma.product.count({ where });
  },

  /**
   * 상품 상세 조회
   */
  async findById(id: number) {
    return prisma.product.findUnique({
      where: { id },
      include: {
        brand: true,
        seller: true,
        detail: true,
        price: true,
        images: {
          orderBy: {
            displayOrder: 'asc',
          },
        },
        optionGroups: {
          include: {
            options: {
              orderBy: {
                displayOrder: 'asc',
              },
            },
          },
          orderBy: {
            displayOrder: 'asc',
          },
        },
        categories: {
          include: {
            category: {
              include: {
                parent: true,
              },
            },
          },
        },
        tags: {
          include: {
            tag: true,
          },
        },
        reviews: {
          include: {
            user: true,
          },
          take: 5,
          orderBy: {
            createdAt: 'desc',
          },
        },
      },
    });
  },

  /**
   * 상품 생성
   */
  async create(data: Prisma.ProductCreateInput) {
    return prisma.product.create({
      data,
    });
  },

  /**
   * 상품 수정
   */
  async update(id: number, data: Prisma.ProductUpdateInput) {
    return prisma.product.update({
      where: { id },
      data,
    });
  },

  /**
   * 상품 삭제
   */
  async delete(id: number) {
    return prisma.product.delete({
      where: { id },
    });
  },

  /**
   * 상품 옵션 추가
   */
  async addOption(
    productId: number,
    optionGroupId: number,
    optionData: Prisma.ProductOptionCreateInput,
  ) {
    return prisma.productOption.create({
      data: optionData,
    });
  },

  /**
   * 상품 옵션 수정
   */
  async updateOption(optionId: number, data: Prisma.ProductOptionUpdateInput) {
    return prisma.productOption.update({
      where: { id: optionId },
      data,
    });
  },

  /**
   * 상품 옵션 삭제
   */
  async deleteOption(optionId: number) {
    return prisma.productOption.delete({
      where: { id: optionId },
    });
  },

  /**
   * 상품 이미지 추가
   */
  async addImage(productId: number, imageData: Prisma.ProductImageCreateInput) {
    return prisma.productImage.create({
      data: imageData,
    });
  },

  /**
   * 상품 옵션 그룹 추가
   */
  async addOptionGroup(productId: number, optionGroupData: Prisma.ProductOptionGroupCreateInput) {
    return prisma.productOptionGroup.create({
      data: optionGroupData,
    });
  },

  /**
   * 상품 옵션 그룹의 옵션들 추가
   */
  async addOptionsToGroup(options: Prisma.ProductOptionCreateManyInput[]) {
    return prisma.productOption.createMany({
      data: options,
    });
  },
};
