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
  ReviewResponse
} from '../types';
import { ProductFilterParams, ProductSortParams } from '../types/product.types';

interface ProductsResult {
  products: ProductListItemResponse[];
  total: number;
  page: number;
  perPage: number;
  totalPages: number;
}

// DB 모델을 API 응답으로 변환하는 헬퍼 함수
function mapProductToListItemResponse(product: any): ProductListItemResponse {
  return {
    id: product.id,
    name: product.name,
    slug: product.slug,
    short_description: product.shortDescription || undefined,
    base_price: product.price?.basePrice || 0,
    sale_price: product.price?.salePrice || undefined,
    currency: product.price?.currency || 'KRW',
    primary_image: product.images.find((img: any) => img.isPrimary) ? {
      url: product.images.find((img: any) => img.isPrimary).url,
      alt_text: product.images.find((img: any) => img.isPrimary).altText || undefined
    } : undefined,
    brand: {
      id: product.brand.id,
      name: product.brand.name
    },
    seller: {
      id: product.sellerId || product.seller?.id,
      name: product.seller?.name || '판매자'
    },
    rating: product.rating?.average,
    review_count: product.rating?.count,
    in_stock: product.stock?.quantity > 0,
    status: product.status,
    created_at: product.createdAt.toISOString()
  };
}

// DB 모델을 상세 API 응답으로 변환하는 헬퍼 함수
function mapProductToDetailResponse(product: any): ProductDetailResponse {
  return {
    id: product.id,
    name: product.name,
    slug: product.slug,
    short_description: product.shortDescription || undefined,
    full_description: product.fullDescription || undefined,
    seller: {
      id: product.seller?.id || product.sellerId,
      name: product.seller?.name || '판매자',
      description: product.seller?.description,
      logo_url: product.seller?.logoUrl,
      rating: product.seller?.rating,
      contact_email: product.seller?.contactEmail,
      contact_phone: product.seller?.contactPhone
    },
    brand: {
      id: product.brand.id,
      name: product.brand.name,
      slug: product.brand.slug,
      description: product.brand.description,
      logo_url: product.brand.logoUrl,
      website: product.brand.website
    },
    status: product.status,
    created_at: product.createdAt.toISOString(),
    updated_at: product.updatedAt.toISOString(),
    detail: {
      weight: product.detail?.weight,
      dimensions: product.detail?.dimensions ? {
        width: product.detail.dimensions.width,
        height: product.detail.dimensions.height,
        depth: product.detail.dimensions.depth
      } : undefined,
      materials: product.detail?.materials,
      country_of_origin: product.detail?.countryOfOrigin,
      warranty_info: product.detail?.warrantyInfo,
      care_instructions: product.detail?.careInstructions,
      additional_info: product.detail?.additionalInfo
    },
    price: {
      base_price: product.price?.basePrice || 0,
      sale_price: product.price?.salePrice,
      currency: product.price?.currency || 'KRW',
      tax_rate: product.price?.taxRate,
      discount_percentage: product.price?.salePrice 
        ? Math.round((1 - (product.price.salePrice / product.price.basePrice)) * 100) 
        : undefined
    },
    categories: product.categories?.map((cat: any) => ({
      id: cat.category.id,
      name: cat.category.name,
      slug: cat.category.slug,
      is_primary: cat.isPrimary || false,
      parent: cat.category.parentId ? {
        id: cat.category.parent?.id,
        name: cat.category.parent?.name,
        slug: cat.category.parent?.slug
      } : undefined
    })) || [],
    option_groups: product.optionGroups?.map((group: any) => ({
      id: group.id,
      name: group.name,
      display_order: group.displayOrder,
      options: group.options?.map((opt: any) => ({
        id: opt.id,
        name: opt.name,
        additional_price: opt.additionalPrice || 0,
        sku: opt.sku || undefined,
        stock: opt.stock || 0,
        display_order: opt.displayOrder
      }))
    })) || [],
    images: product.images.map((img: any) => ({
      id: img.id,
      url: img.url,
      alt_text: img.altText || undefined,
      is_primary: img.isPrimary,
      display_order: img.displayOrder,
      option_id: img.optionId || null
    })),
    tags: product.tags?.map((tag: any) => ({
      id: tag.id,
      name: tag.name,
      slug: tag.slug
    })) || [],
    rating: {
      average: product.rating?.average || 0,
      count: product.rating?.count || 0,
      distribution: product.rating?.distribution || {
        "5": 0,
        "4": 0,
        "3": 0,
        "2": 0,
        "1": 0
      }
    },
    related_products: product.relatedProducts?.map((related: any) => ({
      id: related.id,
      name: related.name,
      slug: related.slug,
      short_description: related.shortDescription,
      primary_image: related.images?.find((img: any) => img.isPrimary) ? {
        url: related.images.find((img: any) => img.isPrimary).url,
        alt_text: related.images.find((img: any) => img.isPrimary).altText
      } : undefined,
      base_price: related.price?.basePrice || 0,
      sale_price: related.price?.salePrice,
      currency: related.price?.currency || 'KRW'
    }))
  };
}

// DB 모델을 옵션 API 응답으로 변환하는 헬퍼 함수
function mapOptionToResponse(option: any): ProductOptionResponse {
  return {
    id: option.id,
    name: option.name,
    additional_price: option.additionalPrice || 0,
    sku: option.sku || undefined,
    stock: option.stock,
    display_order: option.displayOrder
  };
}

// DB 모델을 수정용 옵션 API 응답으로 변환하는 헬퍼 함수
function mapOptionToModifyResponse(option: any): ProductOptionModifyResponse {
  return {
    id: option.id,
    option_group_id: option.optionGroupId,
    name: option.name,
    additional_price: option.additionalPrice || 0,
    sku: option.sku || undefined,
    stock: option.stock,
    display_order: option.displayOrder
  };
}

// DB 모델을 이미지 API 응답으로 변환하는 헬퍼 함수
function mapImageToResponse(image: any): ProductImageResponse {
  return {
    id: image.id,
    url: image.url,
    alt_text: image.altText || undefined,
    is_primary: image.isPrimary,
    display_order: image.displayOrder,
    option_id: image.optionId || undefined
  };
}

/**
 * 리뷰 요약 정보 생성
 */
function generateReviewSummary(reviews: any[]) {
  const count = reviews.length;
  let average = 0;
  const distribution: Record<string, number> = {
    "5": 0,
    "4": 0,
    "3": 0,
    "2": 0,
    "1": 0
  };
  
  if (count > 0) {
    let sum = 0;
    
    reviews.forEach(review => {
      sum += review.rating;
      distribution[review.rating.toString()] += 1;
    });
    
    average = parseFloat((sum / count).toFixed(1));
  }
  
  return {
    average_rating: average,
    total_count: count,
    distribution
  };
}

export const productService = {
  /**
   * 상품 목록 조회
   */
  async getProducts(
    { page, perPage }: PaginationParams, 
    filters?: ProductFilterParams, 
    sorts?: ProductSortParams[]
  ): Promise<ProductsResult> {
    const skip = (page - 1) * perPage;
    const take = perPage;

    const [dbProducts, total] = await Promise.all([
      productRepository.findAll(skip, take, filters, sorts),
      productRepository.count(filters)
    ]);

    const totalPages = Math.ceil(total / perPage);

    return {
      products: dbProducts,
      total,
      page,
      perPage,
      totalPages
    };
  },

  /**
   * 상품 상세 조회
   */
  async getProductById(id: number): Promise<ProductDetailResponse> {
    const product = await productRepository.findById(id);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    // DB 모델을 API 응답 형식으로 변환
    return mapProductToDetailResponse(product);
  },

  /**
   * 상품 생성
   */
  async createProduct(data: Prisma.ProductCreateInput): Promise<ProductDetailResponse> {
    const product = await productRepository.create(data);
    
    if (!product) {
      throw new Error('상품 등록에 실패하였습니다.')
    }

    // DB 모델을 API 응답 형식으로 변환
    return mapProductToDetailResponse(product);
  },

  /**
   * 상품 수정
   */
  async updateProduct(id: number, data: Prisma.ProductUpdateInput): Promise<ProductDetailResponse> {
    const product = await productRepository.findById(id);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const updatedProduct = await productRepository.update(id, data);
    // DB 모델을 API 응답 형식으로 변환
    return mapProductToDetailResponse(updatedProduct);
  },

  /**
   * 상품 삭제
   */
  async deleteProduct(id: number): Promise<void> {
    const product = await productRepository.findById(id);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    await productRepository.delete(id);
  },

  /**
   * 상품 옵션 추가
   */
  async addProductOption(productId: number, optionGroupId: number, optionData: Prisma.ProductOptionCreateInput): Promise<ProductOptionModifyResponse> {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const option = await productRepository.addOption(productId, optionGroupId, optionData);
    // DB 모델을 API 응답 형식으로 변환
    return mapOptionToModifyResponse(option);
  },

  /**
   * 상품 옵션 수정
   */
  async updateProductOption(productId: number, optionId: number, data: Prisma.ProductOptionUpdateInput): Promise<ProductOptionModifyResponse> {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const option = await productRepository.updateOption(optionId, data);
    // DB 모델을 API 응답 형식으로 변환
    return mapOptionToModifyResponse(option);
  },

  /**
   * 상품 옵션 삭제
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
   */
  async addProductImage(productId: number, imageData: Prisma.ProductImageCreateInput): Promise<ProductImageResponse> {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const image = await productRepository.addImage(productId, imageData);
    // DB 모델을 API 응답 형식으로 변환
    return mapImageToResponse(image);
  },

  /**
   * 상품 리뷰 조회
   */
  async getProductReviews(
    productId: number,
    { page, perPage }: PaginationParams,
    rating?: number
  ): Promise<{
    reviews: ReviewResponse[];
    summary: any;
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
      productId
    };
    
    if (rating) {
      where.rating = rating;
    }
    
    // 모든 리뷰 가져오기 (요약 정보 생성용)
    const allReviews = await prisma.review.findMany({
      where: { productId },
      select: { rating: true }
    });
    
    // 페이지네이션된 리뷰 가져오기
    const [reviews, total] = await Promise.all([
      prisma.review.findMany({
        where,
        include: {
          user: true
        },
        orderBy: {
          createdAt: 'desc'
        },
        skip,
        take
      }),
      prisma.review.count({ where })
    ]);
    
    // 리뷰 요약 정보 생성
    const summary = generateReviewSummary(allReviews);
    
    // API 응답 형식으로 변환
    const reviewResponses = reviews.map(review => ({
      id: review.id,
      user: {
        id: review.user.id,
        name: review.user.name || '익명',
        avatar_url: review.user.avatarUrl || undefined
      },
      rating: review.rating,
      title: review.title || '',
      content: review.content || '',
      created_at: review.createdAt.toISOString(),
      updated_at: review.updatedAt.toISOString(),
      verified_purchase: review.verifiedPurchase,
      helpful_votes: review.helpfulVotes
    }));
    
    return {
      reviews: reviewResponses,
      summary,
      total,
      page,
      perPage
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
    }
  ): Promise<ReviewResponse> {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }
    
    const user = await prisma.user.findUnique({
      where: { id: userId }
    });
    
    if (!user) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }
    
    // 이미 작성한 리뷰가 있는지 확인
    const existingReview = await prisma.review.findFirst({
      where: {
        productId,
        userId
      }
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
          connect: { id: productId }
        },
        user: {
          connect: { id: userId }
        }
      },
      include: {
        user: true
      }
    });
    
    // API 응답 형식으로 변환
    return {
      id: review.id,
      user: {
        id: review.user.id,
        name: review.user.name || '익명',
        avatar_url: review.user.avatarUrl || undefined
      },
      rating: review.rating,
      title: review.title || '',
      content: review.content || '',
      created_at: review.createdAt.toISOString(),
      updated_at: review.updatedAt.toISOString(),
      verified_purchase: review.verifiedPurchase,
      helpful_votes: review.helpfulVotes
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
    }
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
        connect: { id: productId }
      }
    });
    
    // 옵션 추가
    if (optionGroupData.options && optionGroupData.options.length > 0) {
      const options = optionGroupData.options.map(option => ({
        name: option.name,
        additionalPrice: option.additional_price || 0,
        sku: option.sku,
        stock: option.stock || 0,
        displayOrder: option.display_order || 0,
        optionGroupId: optionGroup.id
      }));
      
      await productRepository.addOptionsToGroup(options);
    }
    
    // 생성된 옵션 그룹과 옵션 정보 조회
    const createdGroup = await prisma.productOptionGroup.findUnique({
      where: { id: optionGroup.id },
      include: {
        options: true
      }
    });
    
    if (!createdGroup) {
      throw new Error('옵션 그룹 생성에 실패했습니다.');
    }
    
    // API 응답 형식으로 변환
    return {
      id: createdGroup.id,
      name: createdGroup.name,
      display_order: createdGroup.displayOrder,
      options: createdGroup.options.map(option => ({
        id: option.id,
        name: option.name,
        additional_price: option.additionalPrice,
        sku: option.sku || undefined,
        stock: option.stock,
        display_order: option.displayOrder
      }))
    };
  }
};