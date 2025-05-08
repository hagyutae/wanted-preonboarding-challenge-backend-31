import {
  Injectable,
  NotFoundException,
  ForbiddenException,
} from '@nestjs/common';
import { ReviewsRepository } from './reviews.repository';
import {
  GetReviewsRequestDto,
  CreateReviewRequestDto,
  UpdateReviewRequestDto,
} from './dto/review.dto';

@Injectable()
export class ReviewsService {
  constructor(private readonly reviewsRepository: ReviewsRepository) {}

  async getReviews(productId: number, query: GetReviewsRequestDto) {
    const items = await this.reviewsRepository.getReviews(productId, query);
    const summary = await this.reviewsRepository.getReviewsSummary(
      productId,
      query,
    );

    return {
      items,
      summary,
    };
  }

  async createReview(
    productId: number,
    userId: number,
    dto: CreateReviewRequestDto,
  ) {
    return this.reviewsRepository.createReview(productId, userId, dto);
  }

  async updateReview(id: number, userId: number, dto: UpdateReviewRequestDto) {
    const review = await this.reviewsRepository.getReview(id);
    if (!review) {
      throw new NotFoundException('리뷰를 찾을 수 없습니다.');
    }

    if (review.userId !== userId) {
      throw new ForbiddenException(
        '다른 사용자의 리뷰를 수정할 권한이 없습니다.',
      );
    }

    const updatedReview = await this.reviewsRepository.updateReview(id, dto);
    return updatedReview;
  }

  async deleteReview(id: number, userId: number): Promise<void> {
    const review = await this.reviewsRepository.getReview(id);
    if (!review) {
      throw new NotFoundException('리뷰를 찾을 수 없습니다.');
    }

    if (review.userId !== userId) {
      throw new ForbiddenException(
        '다른 사용자의 리뷰를 삭제할 권한이 없습니다.',
      );
    }

    await this.reviewsRepository.deleteReview(id);
  }
}
