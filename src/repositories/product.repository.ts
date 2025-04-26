import { prisma } from '../lib/prisma';
import { Prisma } from '@prisma/client';
import { ProductListItemResponse } from '../types';

export const productRepository = {
  /**
   * 상품 목록 조회
   */
  async findAll(skip: number, take: number): Promise<ProductListItemResponse[]> {
    const products = await prisma.product.findMany({
      include: {
        brand: true,
        seller: true,
        price: true,
        images: {
          where: { isPrimary: true },
          take: 1,
        },
      },
      take,
      skip,
    });

    // DB 모델에서 API 응답 형식으로 변환
    return products.map(product => {
      const primaryImage = product.images.length > 0 ? product.images[0] : null;
      
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
        rating: undefined, // TODO: 리뷰 평점 계산
        review_count: undefined, // TODO: 리뷰 수 계산
        in_stock: true, // TODO: 재고 확인
        status: product.status,
        created_at: product.createdAt.toISOString()
      };
    });
  },

  /**
   * 상품 전체 개수 조회
   */
  async count() {
    return prisma.product.count();
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
}; 