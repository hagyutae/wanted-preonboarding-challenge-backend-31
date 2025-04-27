import { Prisma } from '@prisma/client';
import { productRepository } from '../repositories/product.repository';
import { prisma } from '../lib/prisma';
import { PaginationParams } from '../utils/pagination';
import {
  ProductListItemResponse,
  ProductDetailResponse,
  ProductOptionResponse,
  ProductImageResponse,
  ProductOptionModifyResponse,
  ReviewListResponse,
  ReviewResponse,
} from '../types';
import { ProductFilterParams, ProductSortParams } from '../types/product.types';
import {
  mapProductToListItem,
  mapProductToDetail,
  mapOptionToResponse,
  mapOptionToModifyResponse,
  mapImageToResponse,
  generateReviewSummary,
  ProductWithRelations,
} from '../types/mappers/product.mapper';
import { reviewRepository } from '../repositories/review.repository';
import { mapReviewToApiResponse } from '../types/mappers/review.mapper';

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

interface ProductsResult {
  products: ProductListItemResponse[];
  total: number;
  page: number;
  perPage: number;
  totalPages: number;
}

export const productService = {
  /**
   * 상품 목록을 페이지네이션, 필터링, 정렬 조건에 맞게 조회
   * @param pagination - 페이지네이션 파라미터 (page, perPage)
   * @param filters - 필터링 조건 (선택 사항)
   * @param sorts - 정렬 조건 (선택 사항)
   * @returns 상품 목록 결과와 페이지네이션 정보
   */
  async getProducts(
    { page, perPage }: PaginationParams,
    filters?: ProductFilterParams,
    sorts?: ProductSortParams[],
  ): Promise<ProductsResult> {
    const skip = (page - 1) * perPage;
    const take = perPage;

    const [products, total] = await Promise.all([
      productRepository.findAll(skip, take, filters, sorts),
      productRepository.count(filters),
    ]);

    const totalPages = Math.ceil(total / perPage);

    return {
      products,
      total,
      page,
      perPage,
      totalPages,
    };
  },

  /**
   * ID로 상품 상세 정보 조회
   * @param id - 상품 ID
   * @returns 상품 상세 정보
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async getProductById(id: number): Promise<ProductDetailResponse> {
    const product = await productRepository.findById(id);

    if (!product) {
      throw new Error(`상품 ID ${id}를 찾을 수 없습니다`);
    }

    if (!isProductWithRelations(product)) {
      throw new Error(`상품 ID ${id}에 필요한 관계 정보가 없습니다`);
    }

    return mapProductToDetail(product);
  },

  /**
   * 새 상품 생성
   * @param data - 생성할 상품 데이터
   * @returns 생성된 상품 상세 정보
   * @throws 상품 생성 후 조회 실패 시 에러
   */
  async createProduct(data: Prisma.ProductCreateInput): Promise<ProductDetailResponse> {
    const newProduct = await productRepository.create(data);

    const product = await productRepository.findById(newProduct.id);
    if (!product) {
      throw new Error(`생성된 상품 ID ${newProduct.id}를 찾을 수 없습니다`);
    }

    if (!isProductWithRelations(product)) {
      throw new Error(`상품 ID ${newProduct.id}에 필요한 관계 정보가 없습니다`);
    }

    return mapProductToDetail(product);
  },

  /**
   * 상품 정보 업데이트
   * @param id - 수정할 상품 ID
   * @param data - 업데이트할 상품 데이터
   * @returns 업데이트된 상품 상세 정보
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async updateProduct(id: number, data: Prisma.ProductUpdateInput): Promise<ProductDetailResponse> {
    const existingProduct = await productRepository.findById(id);
    if (!existingProduct) {
      throw new Error(`상품 ID ${id}를 찾을 수 없습니다`);
    }

    await productRepository.update(id, data);

    const updatedProduct = await productRepository.findById(id);
    if (!updatedProduct) {
      throw new Error(`업데이트된 상품 ID ${id}를 찾을 수 없습니다`);
    }

    if (!isProductWithRelations(updatedProduct)) {
      throw new Error(`상품 ID ${id}에 필요한 관계 정보가 없습니다`);
    }

    return mapProductToDetail(updatedProduct);
  },

  /**
   * 상품 삭제 (소프트 삭제)
   * @param id - 상품 ID
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async deleteProduct(id: number): Promise<void> {
    const product = await productRepository.findById(id);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    // 하드 삭제 대신 상태를 'DELETED'로 업데이트
    await productRepository.update(id, {
      status: 'DELETED',
    });
  },

  /**
   * 상품 옵션 추가
   * @param productId - 상품 ID
   * @param optionGroupId - 옵션 그룹 ID
   * @param optionData - 생성할 옵션 데이터
   * @returns 생성된 옵션 정보
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async addProductOption(
    productId: number,
    optionGroupId: number,
    optionData: Prisma.ProductOptionCreateInput,
  ): Promise<ProductOptionModifyResponse> {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const option = await productRepository.addOption(productId, optionGroupId, optionData);
    return mapOptionToModifyResponse(option);
  },

  /**
   * 상품 옵션 수정
   * @param productId - 상품 ID
   * @param optionId - 수정할 옵션 ID
   * @param data - 업데이트할 옵션 데이터
   * @returns 수정된 옵션 정보
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async updateProductOption(
    productId: number,
    optionId: number,
    data: Prisma.ProductOptionUpdateInput,
  ): Promise<ProductOptionModifyResponse> {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const option = await productRepository.updateOption(optionId, data);
    return mapOptionToModifyResponse(option);
  },

  /**
   * 상품 옵션 삭제
   * @param productId - 상품 ID
   * @param optionId - 삭제할 옵션 ID
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async deleteProductOption(productId: number, optionId: number): Promise<void> {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    await productRepository.deleteOption(optionId);
  },

  /**
   * 상품 이미지 추가
   * @param productId - 상품 ID
   * @param imageData - 생성할 이미지 데이터
   * @returns 생성된 이미지 정보
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async addProductImage(
    productId: number,
    imageData: Prisma.ProductImageCreateInput,
  ): Promise<ProductImageResponse> {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const image = await productRepository.addImage(productId, imageData);
    return mapImageToResponse(image);
  },

  /**
   * 상품 리뷰 조회
   * @param productId - 상품 ID
   * @param pagination - 페이지네이션 파라미터 (page, perPage)
   * @param rating - 평점 필터 (선택 사항)
   * @returns 리뷰 목록, 요약 정보 및 페이지네이션 정보
   * @throws 상품을 찾을 수 없는 경우 에러
   */
  async getProductReviews(
    productId: number,
    { page, perPage }: PaginationParams,
    rating?: number,
  ): Promise<{
    reviews: ReviewResponse[];
    summary: ReturnType<typeof generateReviewSummary>;
    total: number;
    page: number;
    perPage: number;
  }> {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const skip = (page - 1) * perPage;
    const take = perPage;

    // 리뷰 필터링 조건 구성
    const where: Prisma.ReviewWhereInput = {
      productId,
    };

    if (rating) {
      where.rating = rating;
    }

    // 모든 리뷰 가져오기 (요약 정보 생성용)
    const allReviews = await prisma.review.findMany({
      where: { productId },
      select: { rating: true },
    });

    // 페이지네이션된 리뷰 가져오기
    const [reviews, total] = await Promise.all([
      prisma.review.findMany({
        where,
        include: {
          user: true,
        },
        orderBy: {
          createdAt: 'desc',
        },
        skip,
        take,
      }),
      prisma.review.count({ where }),
    ]);

    // 리뷰 요약 정보 생성
    const summary = generateReviewSummary(allReviews);

    // API 응답 형식으로 변환
    const reviewResponses = reviews.map((review) => ({
      id: review.id,
      user: {
        id: review.user.id,
        name: review.user.name || '익명',
        avatar_url: review.user.avatarUrl || undefined,
      },
      rating: review.rating,
      title: review.title || '',
      content: review.content || '',
      created_at: review.createdAt.toISOString(),
      updated_at: review.updatedAt.toISOString(),
      verified_purchase: review.verifiedPurchase,
      helpful_votes: review.helpfulVotes,
    }));

    return {
      reviews: reviewResponses,
      summary,
      total,
      page,
      perPage,
    };
  },

  /**
   * 상품 리뷰 작성
   */
  async createProductReview(
    productId: number,
    userId: number,
    reviewData: {
      rating: number;
      title: string;
      content: string;
    },
  ): Promise<ReviewResponse> {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const user = await prisma.user.findUnique({
      where: { id: userId },
    });

    if (!user) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }

    // 이미 작성한 리뷰가 있는지 확인
    const existingReview = await prisma.review.findFirst({
      where: {
        productId,
        userId,
      },
    });

    if (existingReview) {
      throw new Error('이미 이 상품에 대한 리뷰를 작성하셨습니다.');
    }

    // 리뷰 생성
    const review = await prisma.review.create({
      data: {
        rating: reviewData.rating,
        title: reviewData.title,
        content: reviewData.content,
        verifiedPurchase: true, // 실제로는 구매 여부 확인 로직이 필요
        helpfulVotes: 0,
        product: {
          connect: { id: productId },
        },
        user: {
          connect: { id: userId },
        },
      },
      include: {
        user: true,
      },
    });

    // API 응답 형식으로 변환
    return {
      id: review.id,
      user: {
        id: review.user.id,
        name: review.user.name || '익명',
        avatar_url: review.user.avatarUrl || undefined,
      },
      rating: review.rating,
      title: review.title || '',
      content: review.content || '',
      created_at: review.createdAt.toISOString(),
      updated_at: review.updatedAt.toISOString(),
      verified_purchase: review.verifiedPurchase,
      helpful_votes: review.helpfulVotes,
    };
  },

  /**
   * 상품 옵션 그룹 생성
   */
  async createProductOptionGroup(
    productId: number,
    optionGroupData: {
      name: string;
      display_order?: number;
      options?: {
        name: string;
        additional_price?: number;
        sku?: string;
        stock?: number;
        display_order?: number;
      }[];
    },
  ) {
    const product = await productRepository.findById(productId);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    // 옵션 그룹 생성
    const optionGroup = await productRepository.addOptionGroup(productId, {
      name: optionGroupData.name,
      displayOrder: optionGroupData.display_order || 0,
      product: {
        connect: { id: productId },
      },
    });

    // 옵션 추가
    if (optionGroupData.options && optionGroupData.options.length > 0) {
      const options = optionGroupData.options.map((option) => ({
        name: option.name,
        additionalPrice: option.additional_price || 0,
        sku: option.sku,
        stock: option.stock || 0,
        displayOrder: option.display_order || 0,
        optionGroupId: optionGroup.id,
      }));

      await productRepository.addOptionsToGroup(options);
    }

    // 생성된 옵션 그룹과 옵션 정보 조회
    const createdGroup = await prisma.productOptionGroup.findUnique({
      where: { id: optionGroup.id },
      include: {
        options: true,
      },
    });

    if (!createdGroup) {
      throw new Error('옵션 그룹 생성에 실패했습니다.');
    }

    // API 응답 형식으로 변환
    return {
      id: createdGroup.id,
      name: createdGroup.name,
      display_order: createdGroup.displayOrder,
      options: createdGroup.options.map((option) => ({
        id: option.id,
        name: option.name,
        additional_price: option.additionalPrice,
        sku: option.sku || undefined,
        stock: option.stock,
        display_order: option.displayOrder,
      })),
    };
  },
};
