import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  Put,
} from '@nestjs/common';
import { ReviewsService } from './reviews.service';
import {
  UpdateReviewRequestDto,
  UpdateReviewResponseDto,
} from './dto/review.dto';
import { createSuccessResponse } from '~/common/utils/response.util';
import { DeleteResponseDto } from '~/common/dto/common.dto';

@Controller('')
export class ReviewsController {
  constructor(private readonly reviewsService: ReviewsService) {}

  @Put(':id')
  async updateReview(
    @Param('id') id: number,
    @Body() dto: UpdateReviewRequestDto,
  ): Promise<UpdateReviewResponseDto> {
    return createSuccessResponse(
      await this.reviewsService.updateReview(id, dto),
      '리뷰가 성공적으로 수정되었습니다.',
    );
  }

  @Delete(':id')
  async deleteReview(@Param('id') id: number): Promise<DeleteResponseDto> {
    await this.reviewsService.deleteReview(id);
    return createSuccessResponse(null, '리뷰가 성공적으로 삭제되었습니다.');
  }
}
