import { z } from 'zod';
import {
  createPaginatedResponseSchema,
  createSuccessResponseSchema,
  paginationInfoSchema,
  paginationParamsSchema,
} from '~/common/utils/response-schema.util';
import {
  ReviewSchema,
  ReviewSummarySchema,
  ReviewWithRelationsSchema,
} from '../entities/review.entity';

// 리뷰 목록 조회 요청
export const GetReviewsRequestDtoSchema = paginationParamsSchema.extend({
  sort: z
    .enum(['created_at:asc', 'created_at:desc', 'rating:asc', 'rating:desc'])
    .optional()
    .default('created_at:desc'),
  rating: z.number().min(1).max(5).optional(),
});
export type GetReviewsRequestDto = z.infer<typeof GetReviewsRequestDtoSchema>;

// 리뷰 목록 조회 응답
export const GetReviewsResponseDtoSchema = createSuccessResponseSchema(
  z.object({
    items: z.array(ReviewWithRelationsSchema),
    pagination: paginationInfoSchema,
    summary: ReviewSummarySchema,
  }),
);
export type GetReviewsResponseDto = z.infer<typeof GetReviewsResponseDtoSchema>;

// 리뷰 상세 조회 응답
export const GetReviewResponseDtoSchema = createSuccessResponseSchema(
  ReviewWithRelationsSchema,
);
export type GetReviewResponseDto = z.infer<typeof GetReviewResponseDtoSchema>;

// 리뷰 등록 요청
export const CreateReviewRequestDtoSchema = z.object({
  rating: z.number().min(1).max(5),
  title: z.string().optional(),
  content: z.string().optional(),
});
export type CreateReviewRequestDto = z.infer<
  typeof CreateReviewRequestDtoSchema
>;

// 리뷰 등록 응답
export const CreateReviewResponseDtoSchema = createSuccessResponseSchema(
  ReviewWithRelationsSchema,
);
export type CreateReviewResponseDto = z.infer<
  typeof CreateReviewResponseDtoSchema
>;

// 리뷰 수정 요청
export const UpdateReviewRequestDtoSchema =
  CreateReviewRequestDtoSchema.partial();
export type UpdateReviewRequestDto = z.infer<
  typeof UpdateReviewRequestDtoSchema
>;

// 리뷰 수정 응답
export const UpdateReviewResponseDtoSchema = createSuccessResponseSchema(
  ReviewSchema.pick({
    id: true,
    rating: true,
    title: true,
    content: true,
    updatedAt: true,
  }),
);
export type UpdateReviewResponseDto = z.infer<
  typeof UpdateReviewResponseDtoSchema
>;
