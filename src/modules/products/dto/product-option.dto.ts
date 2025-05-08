import { z } from 'zod';
import { createSuccessResponseSchema } from '~/common/utils/response-schema.util';

// 상품 옵션 추가 요청
export const CreateProductOptionRequestDtoSchema = z.object({
  optionGroupId: z.number(),
  name: z.string(),
  additionalPrice: z.number(),
  sku: z.string(),
  stock: z.number(),
  displayOrder: z.number(),
});
export type CreateProductOptionRequestDto = z.infer<
  typeof CreateProductOptionRequestDtoSchema
>;

// 상품 옵션 추가 응답
export const CreateProductOptionResponseDataSchema = z.object({
  id: z.number(),
  optionGroupId: z.number(),
  name: z.string(),
  additionalPrice: z.number(),
  sku: z.string(),
  stock: z.number(),
  displayOrder: z.number(),
});
export type CreateProductOptionResponseData = z.infer<
  typeof CreateProductOptionResponseDataSchema
>;

export const CreateProductOptionResponseDtoSchema = createSuccessResponseSchema(
  CreateProductOptionResponseDataSchema,
);
export type CreateProductOptionResponseDto = z.infer<
  typeof CreateProductOptionResponseDtoSchema
>;

// 상품 옵션 수정 요청
export const UpdateProductOptionRequestDtoSchema = z.object({
  name: z.string(),
  additionalPrice: z.number(),
  sku: z.string(),
  stock: z.number(),
  displayOrder: z.number(),
});
export type UpdateProductOptionRequestDto = z.infer<
  typeof UpdateProductOptionRequestDtoSchema
>;

// 상품 옵션 수정 응답
export const UpdateProductOptionResponseDataSchema =
  CreateProductOptionResponseDataSchema;
export type UpdateProductOptionResponseData = z.infer<
  typeof UpdateProductOptionResponseDataSchema
>;

export const UpdateProductOptionResponseDtoSchema = createSuccessResponseSchema(
  UpdateProductOptionResponseDataSchema,
);
export type UpdateProductOptionResponseDto = z.infer<
  typeof UpdateProductOptionResponseDtoSchema
>;
