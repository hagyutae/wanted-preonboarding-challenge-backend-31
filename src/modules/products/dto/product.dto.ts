import { z } from 'zod';
import {
  createSuccessResponseSchema,
  paginationInfoSchema,
  paginationParamsSchema,
} from '~/common/utils/response-schema.util';
import {
  ProductSchema,
  ProductDetailSchema,
  ProductPriceSchema,
  ProductOptionGroupSchema,
  ProductOptionSchema,
  ProductImageSchema,
  BrandSchema,
  SellerSchema,
  ProductCategorySchema,
  TagSchema,
} from '../entities/product.entity';
import { CategoryWithRelationsSchema } from '~/modules/categories/entities/category.entity';

// 상품 목록 조회 요청
export const GetProductsRequestDtoSchema = paginationParamsSchema.extend({
  sort: z
    .enum(['created_at:asc', 'created_at:desc'])
    .optional()
    .default('created_at:desc'),
  status: z.enum(['ACTIVE', 'OUT_OF_STOCK', 'DELETED']).optional(),
  minPrice: z.number().optional(),
  maxPrice: z.number().optional(),
  category: z.array(z.number()).optional(),
  seller: z.number().optional(),
  brand: z.number().optional(),
  inStock: z.boolean().optional(),
  search: z.string().optional(),
});
export type GetProductsRequestDto = z.infer<typeof GetProductsRequestDtoSchema>;

export const GetProductListResponseDataSchema = ProductSchema.pick({
  id: true,
  name: true,
  slug: true,
  shortDescription: true,
  status: true,
  createdAt: true,
}).extend({
  basePrice: z.number(),
  salePrice: z.number().nullable(),
  currency: z.string(),
  primaryImage: ProductImageSchema.pick({
    url: true,
    altText: true,
  }).nullable(),
  brand: BrandSchema.pick({
    id: true,
    name: true,
    logoUrl: true,
  }).nullable(),
  seller: SellerSchema.pick({
    id: true,
    name: true,
    logoUrl: true,
  }).nullable(),
  rating: z.number().nullable(),
  reviewCount: z.number(),
  inStock: z.boolean(),
});
export type GetProductListResponseData = z.infer<
  typeof GetProductListResponseDataSchema
>;

// 상품 목록 조회 응답
export const GetProductsResponseDtoSchema = createSuccessResponseSchema(
  z.object({
    items: z.array(GetProductListResponseDataSchema),
    pagination: paginationInfoSchema,
  }),
);
export type GetProductsResponseDto = z.infer<
  typeof GetProductsResponseDtoSchema
>;

export const GetProductResponseDataSchema = ProductSchema.extend({
  detail: ProductDetailSchema.nullable(),
  price: ProductPriceSchema.extend({
    discountPercentage: z.number().nullable(),
  }),
  categories: z.array(
    ProductCategorySchema.extend({
      isPrimary: z.boolean(),
      category: CategoryWithRelationsSchema,
    }),
  ),
  optionGroups: z.array(
    ProductOptionGroupSchema.extend({
      id: z.number(),
      options: z.array(
        ProductOptionSchema.extend({
          id: z.number(),
          name: z.string(),
        }),
      ),
    }),
  ),
  images: z.array(
    ProductImageSchema.extend({
      id: z.number(),
      optionId: z.number().nullable(),
    }),
  ),
  tags: z.array(
    TagSchema.pick({
      id: true,
      name: true,
      slug: true,
    }),
  ),
  rating: z.object({
    average: z.number(),
    count: z.number(),
    distribution: z.record(z.number()),
  }),
  relatedProducts: z.array(
    ProductSchema.pick({
      id: true,
      name: true,
      slug: true,
      shortDescription: true,
    }).extend({
      primaryImage: ProductImageSchema.pick({
        url: true,
        altText: true,
      }).nullable(),
      basePrice: z.number(),
      salePrice: z.number().nullable(),
      currency: z.string(),
    }),
  ),
});
export type GetProductResponseData = z.infer<
  typeof GetProductResponseDataSchema
>;

// 상품 상세 조회 응답
export const GetProductResponseDtoSchema = createSuccessResponseSchema(
  GetProductResponseDataSchema,
);
export type GetProductResponseDto = z.infer<typeof GetProductResponseDtoSchema>;

// 상품 등록 요청
export const CreateProductRequestDtoSchema = z.object({
  name: z.string(),
  slug: z.string(),
  shortDescription: z.string().optional(),
  fullDescription: z.string().optional(),
  sellerId: z.number(),
  brandId: z.number().optional(),
  status: z.enum(['ACTIVE', 'OUT_OF_STOCK', 'DELETED']),
  detail: ProductDetailSchema.optional(),
  price: ProductPriceSchema,
  categories: z.array(
    z.object({
      categoryId: z.number(),
      isPrimary: z.boolean(),
    }),
  ),
  optionGroups: z
    .array(
      ProductOptionGroupSchema.extend({
        options: z.array(
          ProductOptionSchema.extend({
            name: z.string(),
          }),
        ),
      }),
    )
    .optional(),
  images: z
    .array(
      ProductImageSchema.extend({
        optionId: z.number().optional(),
      }),
    )
    .optional(),
  tags: z.array(z.number()).optional(),
});
export type CreateProductRequestDto = z.infer<
  typeof CreateProductRequestDtoSchema
>;

export const CraeteProductResponseDataSchema = ProductSchema.pick({
  id: true,
  name: true,
  slug: true,
  createdAt: true,
  updatedAt: true,
});
export type CraeteProductResponseData = z.infer<
  typeof CraeteProductResponseDataSchema
>;

// 상품 등록 응답
export const CreateProductResponseDtoSchema = createSuccessResponseSchema(
  CraeteProductResponseDataSchema,
);
export type CreateProductResponseDto = z.infer<
  typeof CreateProductResponseDtoSchema
>;

// 상품 수정 요청
export const UpdateProductRequestDtoSchema =
  CreateProductRequestDtoSchema.partial();
export type UpdateProductRequestDto = z.infer<
  typeof UpdateProductRequestDtoSchema
>;

export const UpdateProductResponseDataSchema = CraeteProductResponseDataSchema;
export type UpdateProductResponseData = z.infer<
  typeof UpdateProductResponseDataSchema
>;

// 상품 수정 응답
export const UpdateProductResponseDtoSchema = createSuccessResponseSchema(
  UpdateProductResponseDataSchema,
);
export type UpdateProductResponseDto = z.infer<
  typeof UpdateProductResponseDtoSchema
>;
