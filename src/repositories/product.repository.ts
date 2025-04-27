import { prisma } from '../lib/prisma';
import { Prisma } from '@prisma/client';
import { ProductListItemResponse } from '../types';
import { ProductFilterParams, ProductSortParams } from '../types/product.types';

export const productRepository = {
  /**
   * 상품 목록 조회
   */
  async findAll(
    skip: number, 
    take: number, 
    filters?: ProductFilterParams,
    sorts?: ProductSortParams[]
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
      if (filters.category && filters.category.length > 0) {
        where.categories = {
          some: {
            categoryId: {
              in: filters.category
            }
          }
        };
      }
      
      // 태그 필터
      if (filters.tags && filters.tags.length > 0) {
        where.tags = {
          some: {
            tagId: {
              in: filters.tags
            }
          }
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
      
      // 재고 유무 필터 - 옵션의 재고 수량을 확인해야 함
      if (filters.inStock !== undefined) {
        // 옵션이 있고 재고가 1개 이상 있는 상품만 필터링
        where.optionGroups = {
          some: {
            options: {
              some: {
                stock: filters.inStock ? { gt: 0 } : { lte: 0 }
              }
            }
          }
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
          { categories: { some: { category: { name: { contains: filters.search, mode: 'insensitive' } } } } }
        ];
      }
    }
    
    // 정렬 조건 구성
    let orderBy: any[] = [];
    
    if (sorts && sorts.length > 0) {
      orderBy = sorts.map(sort => {
        // 특수 케이스 처리
        if (sort.field === 'price.basePrice') {
          return { price: { basePrice: sort.direction } };
        } else if (sort.field === 'price.salePrice') {
          return { price: { salePrice: sort.direction } };
        } else if (sort.field === 'rating') {
          // TODO: 평점 정렬은 리뷰 평균 값으로 처리 필요
          return { createdAt: sort.direction }; // 임시로 생성일 기준 정렬
        } else {
          // 일반 필드 정렬
          return { [sort.field]: sort.direction };
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
          where: { isPrimary: true },
          take: 1,
        },
        optionGroups: {
          include: {
            options: {
              select: {
                stock: true
              }
            }
          }
        },
        reviews: {
          select: {
            rating: true
          }
        }
      },
      orderBy,
      take,
      skip,
    });

    // DB 모델에서 API 응답 형식으로 변환
    return products.map(product => {
      const primaryImage = product.images.length > 0 ? product.images[0] : null;
      
      // 리뷰 평점 및 개수 계산
      const reviews = product.reviews || [];
      const reviewCount = reviews.length;
      const averageRating = reviewCount > 0
        ? reviews.reduce((sum, review) => sum + review.rating, 0) / reviewCount
        : undefined;
      
      // 재고 확인
      const hasStock = product.optionGroups.some(group => 
        group.options.some(option => option.stock > 0)
      );
      
      return {
        id: product.id,
        name: product.name,
        slug: product.slug,
        short_description: product.shortDescription || undefined,
        base_price: product.price?.basePrice || 0,
        sale_price: product.price?.salePrice || undefined,
        currency: product.price?.currency || 'KRW',
        primary_image: primaryImage ? {
          url: primaryImage.url,
          alt_text: primaryImage.altText || undefined
        } : undefined,
        brand: {
          id: product.brand.id,
          name: product.brand.name
        },
        seller: {
          id: product.seller.id,
          name: product.seller.name
        },
        rating: averageRating,
        review_count: reviewCount,
        in_stock: hasStock,
        status: product.status,
        created_at: product.createdAt.toISOString()
      };
    });
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
      if (filters.category && filters.category.length > 0) {
        where.categories = {
          some: {
            categoryId: {
              in: filters.category
            }
          }
        };
      }
      
      // 태그 필터
      if (filters.tags && filters.tags.length > 0) {
        where.tags = {
          some: {
            tagId: {
              in: filters.tags
            }
          }
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
                stock: filters.inStock ? { gt: 0 } : { lte: 0 }
              }
            }
          }
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
          { categories: { some: { category: { name: { contains: filters.search, mode: 'insensitive' } } } } }
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
        images: true,
        optionGroups: {
          include: {
            options: true,
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
  async addOption(productId: number, optionGroupId: number, optionData: Prisma.ProductOptionCreateInput) {
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