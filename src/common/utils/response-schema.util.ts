// src/common/utils/response-schema.utils.ts
import { z } from 'zod';

/**
 * 성공 응답 Zod 스키마 생성 유틸리티
 * @param dataSchema 데이터 스키마
 * @returns 성공 응답 Zod 스키마
 */
export function createSuccessResponseSchema<T extends z.ZodTypeAny>(
  dataSchema: T,
): z.ZodObject<{
  success: z.ZodLiteral<true>;
  data: T;
  message: z.ZodString;
}> {
  return z.object({
    success: z.literal(true),
    data: dataSchema,
    message: z.string(),
  });
}

/**
 * 페이지네이션 정보 Zod 스키마
 */
export const paginationInfoSchema = z.object({
  total_items: z.number().int().nonnegative(),
  total_pages: z.number().int().positive(),
  current_page: z.number().int().positive(),
  per_page: z.number().int().positive(),
});

/**
 * 페이지네이션 파라미터 Zod 스키마
 */
export const paginationParamsSchema = z.object({
  page: z.coerce.number().int().positive().default(1),
  per_page: z.coerce.number().int().positive().default(10),
});

/**
 * 페이지네이션 데이터 Zod 스키마 생성 유틸리티
 * @param itemSchema 아이템 스키마
 * @returns 페이지네이션 데이터 Zod 스키마
 */
export function createPaginatedDataSchema<T extends z.ZodTypeAny>(
  itemSchema: T,
): z.ZodObject<{
  items: z.ZodArray<T>;
  pagination: z.ZodObject<{
    total_items: z.ZodNumber;
    total_pages: z.ZodNumber;
    current_page: z.ZodNumber;
    per_page: z.ZodNumber;
  }>;
}> {
  return z.object({
    items: z.array(itemSchema),
    pagination: paginationInfoSchema,
  });
}

/**
 * 페이지네이션 응답 Zod 스키마 생성 유틸리티
 * @param itemSchema 아이템 스키마
 * @returns 페이지네이션 응답 Zod 스키마
 */
export function createPaginatedResponseSchema<T extends z.ZodTypeAny>(
  itemSchema: T,
): z.ZodObject<{
  success: z.ZodLiteral<true>;
  data: z.ZodObject<{
    items: z.ZodArray<T>;
    pagination: z.ZodObject<{
      total_items: z.ZodNumber;
      total_pages: z.ZodNumber;
      current_page: z.ZodNumber;
      per_page: z.ZodNumber;
    }>;
  }>;
  message: z.ZodString;
}> {
  return z.object({
    success: z.literal(true),
    data: createPaginatedDataSchema(itemSchema),
    message: z.string(),
  });
}

/**
 * 에러 상세 정보 Zod 스키마
 */
export const errorDetailsSchema = z.record(z.unknown());

/**
 * 에러 정보 Zod 스키마
 */
export const errorInfoSchema = z.object({
  code: z.string(),
  message: z.string(),
  details: errorDetailsSchema.optional(),
});

/**
 * 에러 응답 Zod 스키마
 */
export const errorResponseSchema = z.object({
  success: z.literal(false),
  error: errorInfoSchema,
});
