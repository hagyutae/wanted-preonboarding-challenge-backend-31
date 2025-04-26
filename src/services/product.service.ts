import { Prisma } from '@prisma/client';
import { productRepository } from '../repositories/product.repository';
import { PaginationParams } from '../utils/pagination';
import { 
  ProductListItemResponse, 
  ProductDetailResponse, 
  ProductOptionResponse,
  ProductImageResponse 
} from '../types';

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

export const productService = {
  /**
   * 상품 목록 조회
   */
  async getProducts({ page, perPage }: PaginationParams): Promise<ProductsResult> {
    const skip = (page - 1) * perPage;
    const take = perPage;

    const [dbProducts, total] = await Promise.all([
      productRepository.findAll(skip, take),
      productRepository.count()
    ]);

    // DB 모델을 API 응답 형식으로 변환
    const products = dbProducts.map(mapProductToListItemResponse);

    return {
      products,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage)
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
  async addProductOption(productId: number, optionGroupId: number, optionData: Prisma.ProductOptionCreateInput): Promise<ProductOptionResponse> {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const option = await productRepository.addOption(productId, optionGroupId, optionData);
    // DB 모델을 API 응답 형식으로 변환
    return mapOptionToResponse(option);
  },

  /**
   * 상품 옵션 수정
   */
  async updateProductOption(productId: number, optionId: number, data: Prisma.ProductOptionUpdateInput): Promise<ProductOptionResponse> {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    const option = await productRepository.updateOption(optionId, data);
    // DB 모델을 API 응답 형식으로 변환
    return mapOptionToResponse(option);
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
}; 