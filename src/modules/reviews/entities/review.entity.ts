import { z } from 'zod';
import { UserSchema } from './user.entity';

// 기본 리뷰 스키마
export const ReviewSchema = z.object({
  id: z.number(),
  productId: z.number(),
  userId: z.number().nullable(),
  rating: z.number().min(1).max(5),
  title: z.string().nullable(),
  content: z.string().nullable(),
  createdAt: z.date(),
  updatedAt: z.date(),
  verifiedPurchase: z.boolean(),
  helpfulVotes: z.number(),
});

// 관계를 포함한 리뷰 스키마
export const ReviewWithRelationsSchema = ReviewSchema.extend({
  user: UserSchema.nullable(),
});

// 리뷰 요약 스키마
export const ReviewSummarySchema = z.object({
  averageRating: z.number(),
  totalCount: z.number(),
  distribution: z.record(z.number()),
});

// 타입 추론
export type Review = z.infer<typeof ReviewSchema>;
export type ReviewWithRelations = z.infer<typeof ReviewWithRelationsSchema>;
export type ReviewSummary = z.infer<typeof ReviewSummarySchema>;
