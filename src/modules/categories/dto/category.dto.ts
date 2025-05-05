import { z } from 'zod';
import {
  createPaginatedResponseSchema,
  createSuccessResponseSchema,
  paginationInfoSchema,
  paginationParamsSchema,
} from '~/common/utils/response-schema.util';
import { CategoryWithChildrenSchema } from '../entities/category.entity';
import { ProductSelectSchema } from '~/modules/products/entities/product.entity';

// 카테고리 목록 조회 요청 스키마
export const GetCategoriesRequestDtoSchema = z.object({
  level: z.number().min(1).max(3).optional(),
});
export type GetCategoriesRequestDto = z.infer<
  typeof GetCategoriesRequestDtoSchema
>;

// 카테고리 목록 조회 응답 스키마
export const GetCategoriesResponseDtoSchema = createSuccessResponseSchema(
  z.array(CategoryWithChildrenSchema),
);
export type GetCategoriesResponseDto = z.infer<
  typeof GetCategoriesResponseDtoSchema
>;

// 카테고리별 상품 조회 요청 스키마
export const GetProductsByCategoryIdRequestDtoSchema =
  paginationParamsSchema.extend({
    sort: z
      .enum(['created_at:asc', 'created_at:desc'])
      .optional()
      .default('created_at:desc'),
    includeSubCategories: z.boolean().optional().default(true),
  });
export type GetProductsByCategoryIdRequestDto = z.infer<
  typeof GetProductsByCategoryIdRequestDtoSchema
>;

// 카테고리별 상품 조회 응답 스키마
export const GetProductsByCategoryIdResponseDtoSchema =
  createSuccessResponseSchema(
    z.object({
      category: CategoryWithChildrenSchema.extend({
        parent: CategoryWithChildrenSchema.optional(),
      }),
      items: z.array(ProductSelectSchema),
      pagination: paginationInfoSchema,
    }),
  );
export type GetProductsByCategoryIdResponseDto = z.infer<
  typeof GetProductsByCategoryIdResponseDtoSchema
>;
