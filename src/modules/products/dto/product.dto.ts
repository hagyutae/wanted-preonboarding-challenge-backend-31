import { z } from 'zod';
import {
  createPaginatedResponseSchema,
  createSuccessResponseSchema,
  paginationInfoSchema,
  paginationParamsSchema,
} from '~/common/utils/response-schema.util';
import { ProductWithRelationsSchema } from '../entities/product.entity';

// 상품 목록 조회 요청
export const GetProductsRequestDtoSchema = paginationParamsSchema.extend({
  sort: z
    .enum(['created_at:asc', 'created_at:desc', 'price:asc', 'price:desc'])
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

// 상품 목록 조회 응답
export const GetProductsResponseDtoSchema = createSuccessResponseSchema(
  z.object({
    items: z.array(
      z.object({
        id: z.number(),
        name: z.string(),
        slug: z.string(),
        shortDescription: z.string().nullable(),
        basePrice: z.number(),
        salePrice: z.number().nullable(),
        currency: z.string(),
        primaryImage: z
          .object({
            url: z.string(),
            altText: z.string().nullable(),
          })
          .nullable(),
        brand: z
          .object({
            id: z.number(),
            name: z.string(),
          })
          .nullable(),
        seller: z
          .object({
            id: z.number(),
            name: z.string(),
          })
          .nullable(),
        rating: z.number().nullable(),
        reviewCount: z.number(),
        inStock: z.boolean(),
        status: z.string(),
        createdAt: z.date(),
      }),
    ),
    pagination: paginationInfoSchema,
  }),
);
export type GetProductsResponseDto = z.infer<
  typeof GetProductsResponseDtoSchema
>;

// 상품 상세 조회 응답
export const GetProductResponseDtoSchema = createSuccessResponseSchema(
  ProductWithRelationsSchema,
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
  detail: z
    .object({
      weight: z.number().optional(),
      dimensions: z.record(z.any()).optional(),
      materials: z.string().optional(),
      countryOfOrigin: z.string().optional(),
      warrantyInfo: z.string().optional(),
      careInstructions: z.string().optional(),
      additionalInfo: z.record(z.any()).optional(),
    })
    .optional(),
  price: z.object({
    basePrice: z.number(),
    salePrice: z.number().optional(),
    costPrice: z.number().optional(),
    currency: z.string().default('KRW'),
    taxRate: z.number().optional(),
  }),
  categories: z.array(
    z.object({
      categoryId: z.number(),
      isPrimary: z.boolean(),
    }),
  ),
  optionGroups: z
    .array(
      z.object({
        name: z.string(),
        displayOrder: z.number(),
        options: z.array(
          z.object({
            name: z.string(),
            additionalPrice: z.number(),
            sku: z.string().optional(),
            stock: z.number(),
            displayOrder: z.number(),
          }),
        ),
      }),
    )
    .optional(),
  images: z
    .array(
      z.object({
        url: z.string(),
        altText: z.string().optional(),
        isPrimary: z.boolean(),
        displayOrder: z.number(),
        optionId: z.number().optional(),
      }),
    )
    .optional(),
  tags: z.array(z.number()).optional(),
});
export type CreateProductRequestDto = z.infer<
  typeof CreateProductRequestDtoSchema
>;

// 상품 등록 응답
export const CreateProductResponseDtoSchema = createSuccessResponseSchema(
  z.object({
    id: z.number(),
    name: z.string(),
    slug: z.string(),
    createdAt: z.date(),
    updatedAt: z.date(),
  }),
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

// 상품 수정 응답
export const UpdateProductResponseDtoSchema = createSuccessResponseSchema(
  z.object({
    id: z.number(),
    name: z.string(),
    slug: z.string(),
    updatedAt: z.date(),
  }),
);
export type UpdateProductResponseDto = z.infer<
  typeof UpdateProductResponseDtoSchema
>;
