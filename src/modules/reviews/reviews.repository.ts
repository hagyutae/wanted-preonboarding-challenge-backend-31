import { Injectable } from '@nestjs/common';
import { DrizzleService } from '~/database/drizzle.service';
import { and, asc, desc, eq, gte, or, sql } from 'drizzle-orm';
import { reviews as reviewsSchema } from '~/database/schema';
import {
  GetReviewsRequestDto,
  CreateReviewRequestDto,
  UpdateReviewRequestDto,
} from './dto/review.dto';
import { ReviewWithRelations, ReviewSummary } from './entities/review.entity';
import { DeepPartial } from '~/common/types/helper.type';

@Injectable()
export class ReviewsRepository {
  constructor(private readonly drizzleService: DrizzleService) {}

  async getReviews(
    productId: number,
    query: GetReviewsRequestDto,
  ): Promise<ReviewWithRelations[]> {
    const { page = 1, per_page = 10, sort = 'created_at:desc', rating } = query;
    const offset = (page - 1) * per_page;

    return this.drizzleService.db.query.reviews.findMany({
      with: {
        user: true,
        product: true,
      },
      where: and(
        eq(reviewsSchema.productId, productId),
        rating ? gte(reviewsSchema.rating, rating) : undefined,
      ),
      orderBy: this.getOrderBy(sort),
      limit: per_page,
      offset: offset,
    });
  }

  async getReviewsSummary(
    productId: number,
    query: GetReviewsRequestDto,
  ): Promise<ReviewSummary> {
    const { rating } = query;
    const summaryResult = await this.drizzleService.db
      .select({
        averageRating: sql`avg(rating)`,
        distribution: sql`jsonb_object_agg(rating, count)`,
      })
      .from(
        this.drizzleService.db
          .select({
            rating: reviewsSchema.rating,
            count: sql`count(*)`,
          })
          .from(reviewsSchema)
          .where(
            and(
              eq(reviewsSchema.productId, productId),
              eq(reviewsSchema.rating, rating),
            ),
          )
          .groupBy(reviewsSchema.rating)
          .as('rating_counts'),
      );

    const totalResult = await this.drizzleService.db
      .select({ count: sql`count(*)` })
      .from(reviewsSchema)
      .where(
        and(
          eq(reviewsSchema.productId, productId),
          rating ? gte(reviewsSchema.rating, rating) : undefined,
        ),
      );

    return {
      averageRating: Number(summaryResult[0].averageRating) || 0,
      distribution:
        (summaryResult[0].distribution as Record<string, number>) || {},
      totalCount: Number(totalResult[0].count),
    };
  }

  async getReview(
    id: number,
    columns?: DeepPartial<
      Record<keyof typeof reviewsSchema.$inferInsert, boolean>
    >,
  ): Promise<ReviewWithRelations | null> {
    return this.drizzleService.db.query.reviews.findFirst({
      with: {
        user: true,
      },
      where: eq(reviewsSchema.id, id),
      columns,
    });
  }

  async createReview(
    productId: number,
    userId: number,
    dto: CreateReviewRequestDto,
  ): Promise<ReviewWithRelations> {
    const now = new Date();
    const [review] = await this.drizzleService.db
      .insert(reviewsSchema)
      .values({
        id: undefined,
        productId,
        rating: dto.rating,
        title: dto.title,
        content: dto.content,
        verifiedPurchase: true,
        userId,
        helpfulVotes: 0,
        createdAt: now,
        updatedAt: now,
      })
      .returning({
        id: reviewsSchema.id,
      });

    return this.getReview(review.id);
  }

  async updateReview(
    id: number,
    dto: UpdateReviewRequestDto,
  ): Promise<ReviewWithRelations> {
    const [review] = await this.drizzleService.db
      .update(reviewsSchema)
      .set({
        rating: dto.rating,
        title: dto.title,
        content: dto.content,
        updatedAt: new Date(),
      })
      .where(eq(reviewsSchema.id, id))
      .returning({
        id: reviewsSchema.id,
      });

    return this.getReview(review.id, {
      id: true,
      rating: true,
      title: true,
      content: true,
    });
  }

  async deleteReview(id: number): Promise<void> {
    await this.drizzleService.db
      .delete(reviewsSchema)
      .where(eq(reviewsSchema.id, id));
  }

  private getOrderBy(sort: string) {
    const [field, order] = sort.split(':');
    const fieldMap = {
      created_at: reviewsSchema.createdAt,
      rating: reviewsSchema.rating,
    };

    if (!fieldMap[field]) {
      return desc(reviewsSchema.createdAt);
    }

    return order === 'desc' ? desc(fieldMap[field]) : asc(fieldMap[field]);
  }
}
