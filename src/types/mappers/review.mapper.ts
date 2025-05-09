import { Review, User } from '@prisma/client';
import { ReviewResponse } from '../responses/review.response';

export type ReviewWithRelations = Review & {
  user: User;
};

/**
 * 리뷰 DB 모델을 API 응답으로 변환
 */
export function mapReviewToApiResponse(review: ReviewWithRelations): ReviewResponse {
  return {
    id: review.id,
    user: {
      id: review.user.id,
      name: review.user.name ?? '사용자',
      avatar_url: review.user.avatarUrl ?? undefined,
    },
    rating: review.rating,
    title: review.title ?? undefined,
    content: review.content ?? undefined,
    created_at: review.createdAt.toISOString(),
    updated_at: review.updatedAt.toISOString(),
    verified_purchase: review.verifiedPurchase,
    helpful_votes: review.helpfulVotes,
  };
}
