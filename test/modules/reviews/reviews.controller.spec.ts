import { Test, TestingModule } from '@nestjs/testing';
import { ReviewsController } from '~/modules/reviews/reviews.controller';
import { ReviewsService } from '~/modules/reviews/reviews.service';

describe('ReviewsController', () => {
  let controller: ReviewsController;
  let service: ReviewsService;

  const mockReviewsService = {
    updateReview: jest.fn(),
    deleteReview: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ReviewsController],
      providers: [
        {
          provide: ReviewsService,
          useValue: mockReviewsService,
        },
      ],
    }).compile();

    controller = module.get<ReviewsController>(ReviewsController);
    service = module.get<ReviewsService>(ReviewsService);
  });

  describe('updateReview', () => {
    it('리뷰를 수정하고 성공 응답을 반환해야 함', async () => {
      const userId = 1;
      const reviewId = 1;
      const dto = {
        rating: 4,
        title: '수정된 리뷰',
        content: '수정된 리뷰 내용',
      };

      const mockUpdatedReview = {
        id: 1,
        userId,
        productId: 1,
        ...dto,
        createdAt: new Date(),
        updatedAt: new Date(),
      };

      mockReviewsService.updateReview.mockResolvedValue(mockUpdatedReview);

      const result = await controller.updateReview(reviewId, dto, userId);

      expect(result).toEqual({
        success: true,
        message: '리뷰가 성공적으로 수정되었습니다.',
        data: mockUpdatedReview,
      });
      expect(service.updateReview).toHaveBeenCalledWith(userId, reviewId, dto);
    });
  });

  describe('deleteReview', () => {
    it('리뷰를 삭제하고 성공 응답을 반환해야 함', async () => {
      const userId = 1;
      const reviewId = 1;

      mockReviewsService.deleteReview.mockResolvedValue(undefined);

      const result = await controller.deleteReview(reviewId, userId);

      expect(result).toEqual({
        success: true,
        message: '리뷰가 성공적으로 삭제되었습니다.',
        data: null,
      });
      expect(service.deleteReview).toHaveBeenCalledWith(userId, reviewId);
    });
  });
});
