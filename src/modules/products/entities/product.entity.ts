import { z } from 'zod';
import { CategoryWithRelationsSchema } from '~/modules/categories/entities/category.entity';
import { ReviewSchema } from '~/modules/reviews/entities/review.entity';
import { UserSchema } from '~/modules/reviews/entities/user.entity';

// 기본 상품 스키마
export const ProductSchema = z.object({
  id: z.number(),
  name: z.string(),
  slug: z.string(),
  shortDescription: z.string().nullable(),
  fullDescription: z.string().nullable(),
  sellerId: z.number(),
  brandId: z.number().nullable(),
  status: z.string(),
  stock: z.number(),
  rating: z.number().nullable(),
  reviewCount: z.number().nullable(),
  createdAt: z.date(),
  updatedAt: z.date(),
});

// 상품 상세 스키마
export const ProductDetailSchema = z.object({
  weight: z.number(),
  dimensions: z.object({
    width: z.number(),
    height: z.number(),
    depth: z.number(),
  }),
  materials: z.string(),
  countryOfOrigin: z.string(),
  warrantyInfo: z.string(),
  careInstructions: z.string(),
  additionalInfo: z.record(z.unknown()),
});

// 상품 가격 스키마
export const ProductPriceSchema = z.object({
  basePrice: z.number(),
  salePrice: z.number().optional(),
  costPrice: z.number(),
  currency: z.string(),
  taxRate: z.number(),
});

// 상품 옵션 그룹 스키마
export const ProductOptionGroupSchema = z.object({
  name: z.string(),
  displayOrder: z.number(),
});

// 상품 옵션 스키마
export const ProductOptionSchema = z.object({
  additionalPrice: z.number(),
  sku: z.string(),
  stock: z.number(),
  displayOrder: z.number(),
});

// 상품 이미지 스키마
export const ProductImageSchema = z.object({
  url: z.string(),
  altText: z.string(),
  isPrimary: z.boolean(),
  displayOrder: z.number(),
});

// 브랜드 스키마
export const BrandSchema = z.object({
  id: z.number(),
  name: z.string(),
  slug: z.string(),
  description: z.string().nullable(),
  logoUrl: z.string().nullable(),
  website: z.string().nullable(),
});

// 판매자 스키마
export const SellerSchema = z.object({
  id: z.number(),
  name: z.string(),
  description: z.string().nullable(),
  logoUrl: z.string().nullable(),
  rating: z.number().nullable(),
  contactEmail: z.string().nullable(),
  contactPhone: z.string().nullable(),
  createdAt: z.date(),
});

export const ProductCategorySchema = z.object({
  id: z.number(),
  name: z.string(),
  slug: z.string(),
  parentId: z.number().nullable(),
  level: z.number(),
});

// 관계를 포함한 상품 스키마
export const ProductWithRelationsSchema = ProductSchema.extend({
  detail: ProductDetailSchema.optional(),
  price: ProductPriceSchema,
  categories: z.array(
    CategoryWithRelationsSchema.pick({
      id: true,
      name: true,
      slug: true,
      parent: true,
    }),
  ),
  optionGroups: z.array(
    ProductOptionGroupSchema.extend({
      options: z.array(ProductOptionSchema),
    }),
  ),
  images: z.array(ProductImageSchema),
  primaryImage: ProductImageSchema.pick({
    url: true,
    altText: true,
  }).optional(),
  brand: BrandSchema.pick({
    id: true,
    name: true,
    description: true,
    logoUrl: true,
    website: true,
  }).optional(),
  seller: SellerSchema.pick({
    id: true,
    name: true,
    description: true,
    logoUrl: true,
    rating: true,
    contactEmail: true,
    contactPhone: true,
  }).optional(),
  reviews: z
    .array(
      ReviewSchema.extend({
        user: UserSchema.pick({
          id: true,
          name: true,
          avatarUrl: true,
        }),
      }),
    )
    .optional(),
});

export const ProductResponseSchema = ProductWithRelationsSchema.extend({
  basePrice: z.number(),
  salePrice: z.number(),
  currency: z.string(),
  inStock: z.boolean(),
});

// 타입 추론
export type Product = z.infer<typeof ProductSchema>;
export type ProductWithRelations = z.infer<typeof ProductWithRelationsSchema>;
export type ProductDetail = z.infer<typeof ProductDetailSchema>;
export type ProductPrice = z.infer<typeof ProductPriceSchema>;
export type ProductOptionGroup = z.infer<typeof ProductOptionGroupSchema>;
export type ProductOption = z.infer<typeof ProductOptionSchema>;
export type ProductImage = z.infer<typeof ProductImageSchema>;
export type Brand = z.infer<typeof BrandSchema>;
export type Seller = z.infer<typeof SellerSchema>;
export type ProductCategory = z.infer<typeof ProductCategorySchema>;
