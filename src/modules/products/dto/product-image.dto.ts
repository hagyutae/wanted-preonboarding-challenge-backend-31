import { z } from 'zod';
import { createSuccessResponseSchema } from '~/common/utils/response-schema.util';

// 상품 이미지 추가 요청
export const CreateProductImageRequestDtoSchema = z.object({
  url: z.string(),
  altText: z.string(),
  isPrimary: z.boolean(),
  displayOrder: z.number(),
  optionId: z.number().optional(),
});
export type CreateProductImageRequestDto = z.infer<
  typeof CreateProductImageRequestDtoSchema
>;

// 상품 이미지 추가 응답
export const CreateProductImageResponseDataSchema = z.object({
  id: z.number(),
  url: z.string(),
  altText: z.string(),
  isPrimary: z.boolean(),
  displayOrder: z.number(),
  optionId: z.number().nullable(),
});
export type CreateProductImageResponseData = z.infer<
  typeof CreateProductImageResponseDataSchema
>;

export const CreateProductImageResponseDtoSchema = createSuccessResponseSchema(
  CreateProductImageResponseDataSchema,
);
export type CreateProductImageResponseDto = z.infer<
  typeof CreateProductImageResponseDtoSchema
>;
