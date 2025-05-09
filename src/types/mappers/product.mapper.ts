import {
  Prisma,
  Product,
  ProductDetail,
  ProductImage,
  ProductOption,
  ProductOptionGroup,
} from '@prisma/client';
import {
  ProductDetailResponse,
  ProductImageResponse,
  ProductListItemResponse,
  ProductOptionModifyResponse,
  ProductOptionResponse,
} from '../responses/product.response';

// 추가: JSON 차원 정보에 대한 인터페이스 정의
interface ProductDimensions {
  width: number;
  height: number;
  depth: number;
}

// 확장된 Product 타입 정의 (모든 include된 관계를 포함)
export type ProductWithRelations = Product & {
  seller?: {
    id: number;
    name: string;
    description?: string;
    logoUrl?: string;
    rating?: number;
    contactEmail?: string;
    contactPhone?: string;
  };
  brand: {
    id: number;
    name: string;
    slug: string;
    description?: string;
    logoUrl?: string;
    website?: string;
  };
  detail?: ProductDetail;
  price?: {
    basePrice: number;
    salePrice?: number;
    currency: string;
    taxRate?: number;
    costPrice?: number;
  };
  images: ProductImage[];
  optionGroups?: (ProductOptionGroup & {
    options?: ProductOption[];
  })[];
  categories?: {
    isPrimary: boolean;
    category: {
      id: number;
      name: string;
      slug: string;
      parentId?: number;
      parent?: {
        id: number;
        name: string;
        slug: string;
      };
    };
  }[];
  tags?: {
    tag: {
      id: number;
      name: string;
      slug: string;
    };
  }[];
  rating?: {
    average: number;
    count: number;
    distribution: Record<string, number>;
  };
  relatedProducts?: ProductWithRelations[];
};

// 추가: additionalInfo의 타입 가드 함수
function isValidAdditionalInfo(
  obj: unknown,
): obj is Record<string, string | number | boolean | null> {
  return obj !== null && typeof obj === 'object';
}

/**
 * 상품 목록 항목으로 변환하는 매퍼
 */
export function mapProductToListItem(product: ProductWithRelations): ProductListItemResponse {
  const primaryImage = product.images.find((img) => img.isPrimary);

  return {
    id: product.id,
    name: product.name,
    slug: product.slug,
    short_description: product.shortDescription || undefined,
    base_price: product.price?.basePrice || 0,
    sale_price: product.price?.salePrice || undefined,
    currency: product.price?.currency || 'KRW',
    primary_image: primaryImage
      ? {
          url: primaryImage.url,
          alt_text: primaryImage.altText || undefined,
        }
      : undefined,
    brand: {
      id: product.brand.id,
      name: product.brand.name,
    },
    seller: {
      id: product.seller?.id || product.sellerId,
      name: product.seller?.name || '판매자',
    },
    rating: product.rating?.average,
    review_count: product.rating?.count,
    in_stock:
      product.optionGroups?.some((group) => group.options?.some((option) => option.stock > 0)) ??
      false,
    status: product.status,
    created_at: product.createdAt.toISOString(),
  };
}

/**
 * 상품 상세 정보로 변환하는 매퍼
 */
export function mapProductToDetail(product: ProductWithRelations): ProductDetailResponse {
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
      contact_phone: product.seller?.contactPhone,
    },
    brand: {
      id: product.brand.id,
      name: product.brand.name,
      slug: product.brand.slug,
      description: product.brand.description,
      logo_url: product.brand.logoUrl,
      website: product.brand.website,
    },
    status: product.status,
    created_at: product.createdAt.toISOString(),
    updated_at: product.updatedAt.toISOString(),
    detail: {
      weight: product.detail?.weight ?? undefined,
      dimensions: product.detail?.dimensions
        ? (() => {
            const dim = product.detail.dimensions;
            // Prisma에서 JSON 형태로 저장된 dimensions를 적절히 변환
            if (
              dim &&
              typeof dim === 'object' &&
              'width' in dim &&
              'height' in dim &&
              'depth' in dim
            ) {
              return {
                width: Number(dim.width),
                height: Number(dim.height),
                depth: Number(dim.depth),
              };
            }
            return undefined;
          })()
        : undefined,
      materials: product.detail?.materials ?? undefined,
      country_of_origin: product.detail?.countryOfOrigin ?? undefined,
      warranty_info: product.detail?.warrantyInfo ?? undefined,
      care_instructions: product.detail?.careInstructions ?? undefined,
      additional_info:
        product.detail?.additionalInfo && isValidAdditionalInfo(product.detail.additionalInfo)
          ? product.detail.additionalInfo
          : undefined,
    },
    price: {
      base_price: product.price?.basePrice || 0,
      sale_price: product.price?.salePrice,
      currency: product.price?.currency || 'KRW',
      tax_rate: product.price?.taxRate,
      discount_percentage:
        product.price?.salePrice && product.price.basePrice
          ? Math.round((1 - product.price.salePrice / product.price.basePrice) * 100)
          : undefined,
    },
    categories:
      product.categories?.map((cat) => ({
        id: cat.category.id,
        name: cat.category.name,
        slug: cat.category.slug,
        is_primary: cat.isPrimary || false,
        parent: cat.category.parentId
          ? {
              id: cat.category.parent?.id || 0,
              name: cat.category.parent?.name || '',
              slug: cat.category.parent?.slug || '',
            }
          : undefined,
      })) || [],
    option_groups:
      product.optionGroups?.map((group) => ({
        id: group.id,
        name: group.name,
        display_order: group.displayOrder,
        options: group.options?.map((opt) => mapOptionToResponse(opt)) || [],
      })) || [],
    images: product.images.map((img) => mapImageToResponse(img)),
    tags:
      product.tags?.map((tagRel) => ({
        id: tagRel.tag.id,
        name: tagRel.tag.name,
        slug: tagRel.tag.slug,
      })) || [],
    rating: {
      average: product.rating?.average || 0,
      count: product.rating?.count || 0,
      distribution: product.rating?.distribution || {
        '5': 0,
        '4': 0,
        '3': 0,
        '2': 0,
        '1': 0,
      },
    },
    related_products:
      product.relatedProducts?.map((related) => ({
        id: related.id,
        name: related.name,
        slug: related.slug,
        short_description: related.shortDescription ?? undefined,
        primary_image: related.images.find((img) => img.isPrimary)
          ? {
              url: related.images.find((img) => img.isPrimary)?.url || '',
              alt_text: related.images.find((img) => img.isPrimary)?.altText ?? undefined,
            }
          : undefined,
        base_price: related.price?.basePrice || 0,
        sale_price: related.price?.salePrice,
        currency: related.price?.currency || 'KRW',
      })) || [],
  };
}

/**
 * 상품 옵션 정보로 변환하는 매퍼
 */
export function mapOptionToResponse(option: ProductOption): ProductOptionResponse {
  return {
    id: option.id,
    name: option.name,
    additional_price: option.additionalPrice || 0,
    sku: option.sku || undefined,
    stock: option.stock,
    display_order: option.displayOrder,
  };
}

/**
 * 수정용 상품 옵션 정보로 변환하는 매퍼
 */
export function mapOptionToModifyResponse(option: ProductOption): ProductOptionModifyResponse {
  return {
    id: option.id,
    option_group_id: option.optionGroupId,
    name: option.name,
    additional_price: option.additionalPrice || 0,
    sku: option.sku || undefined,
    stock: option.stock,
    display_order: option.displayOrder,
  };
}

/**
 * 상품 이미지 정보로 변환하는 매퍼
 */
export function mapImageToResponse(image: ProductImage): ProductImageResponse {
  return {
    id: image.id,
    url: image.url,
    alt_text: image.altText || undefined,
    is_primary: image.isPrimary,
    display_order: image.displayOrder,
    option_id: image.optionId || undefined,
  };
}

/**
 * 리뷰 요약 정보 생성
 */
export function generateReviewSummary(reviews: { rating: number }[]) {
  const count = reviews.length;
  let average = 0;
  const distribution: Record<string, number> = {
    '5': 0,
    '4': 0,
    '3': 0,
    '2': 0,
    '1': 0,
  };

  if (count > 0) {
    reviews.forEach((review) => {
      distribution[review.rating.toString()] = (distribution[review.rating.toString()] || 0) + 1;
      average += review.rating;
    });

    average = Math.round((average / count) * 10) / 10;
  }

  return {
    average,
    count,
    distribution,
  };
}
