import { Test, TestingModule } from '@nestjs/testing';
import { ReviewsService } from '~/modules/reviews/reviews.service';
import { ReviewsRepository } from '~/modules/reviews/reviews.repository';
import { NotFoundException, ForbiddenException } from '@nestjs/common';

describe('ReviewsService', () => {
  let service: ReviewsService;
  let repository: ReviewsRepository;

  const mockReviewsRepository = {
    getReviews: jest.fn(),
    getReviewsSummary: jest.fn(),
    getReview: jest.fn(),
    createReview: jest.fn(),
    updateReview: jest.fn(),
    deleteReview: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ReviewsService,
        {
          provide: ReviewsRepository,
          useValue: mockReviewsRepository,
        },
      ],
    }).compile();

    service = module.get<ReviewsService>(ReviewsService);
    repository = module.get<ReviewsRepository>(ReviewsRepository);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('getReviews', () => {
    it('리뷰 목록을 조회하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const query = { page: 1, per_page: 10 };
      const mockReviews = {
        items: [
          {
            id: 1,
            content: '테스트 리뷰',
            rating: 5,
          },
        ],
        summary: {
          totalCount: 1,
          averageRating: 5,
        },
      };

      mockReviewsRepository.getReviews.mockResolvedValue(mockReviews.items);
      mockReviewsRepository.getReviewsSummary.mockResolvedValue(
        mockReviews.summary,
      );

      const result = await service.getReviews(productId, query);

      expect(result).toEqual(mockReviews);
      expect(repository.getReviews).toHaveBeenCalledWith(productId, query);
    });
  });

  describe('createReview', () => {
    it('리뷰를 생성하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const userId = 1;
      const dto = {
        content: '테스트 리뷰',
        rating: 5,
      };

      const mockReview = {
        id: 1,
        productId,
        userId,
        ...dto,
      };

      mockReviewsRepository.createReview.mockResolvedValue(mockReview);

      const result = await service.createReview(productId, userId, dto);

      expect(result).toEqual(mockReview);
      expect(repository.createReview).toHaveBeenCalledWith(
        productId,
        userId,
        dto,
      );
    });
  });

  describe('updateReview', () => {
    it('리뷰가 존재하지 않으면 NotFoundException을 던져야 함', async () => {
      const reviewId = 1;
      const userId = 1;
      const dto = {
        rating: 4,
        title: '수정된 리뷰',
        content: '수정된 리뷰 내용',
      };

      mockReviewsRepository.getReview.mockResolvedValue(null);

      await expect(service.updateReview(reviewId, userId, dto)).rejects.toThrow(
        NotFoundException,
      );
    });

    it('리뷰 작성자가 아니면 ForbiddenException을 던져야 함', async () => {
      const reviewId = 1;
      const userId = 2;
      const dto = {
        rating: 4,
        title: '수정된 리뷰',
        content: '수정된 리뷰 내용',
      };

      const mockReview = {
        id: reviewId,
        userId: 1,
        rating: 5,
        title: '기존 리뷰',
        content: '기존 리뷰 내용',
      };

      mockReviewsRepository.getReview.mockResolvedValue(mockReview);

      await expect(service.updateReview(reviewId, userId, dto)).rejects.toThrow(
        ForbiddenException,
      );
    });

    it('리뷰 작성자이면 리뷰를 수정해야 함', async () => {
      const reviewId = 1;
      const userId = 1;
      const dto = {
        rating: 4,
        title: '수정된 리뷰',
        content: '수정된 리뷰 내용',
      };

      const mockReview = {
        id: reviewId,
        userId: 1,
        rating: 5,
        title: '기존 리뷰',
        content: '기존 리뷰 내용',
      };

      const mockUpdatedReview = {
        ...mockReview,
        ...dto,
      };

      mockReviewsRepository.getReview.mockResolvedValue(mockReview);
      mockReviewsRepository.updateReview.mockResolvedValue(mockUpdatedReview);

      const result = await service.updateReview(reviewId, userId, dto);

      expect(result).toEqual(mockUpdatedReview);
      expect(repository.updateReview).toHaveBeenCalledWith(reviewId, dto);
    });
  });

  describe('deleteReview', () => {
    it('리뷰가 존재하지 않으면 NotFoundException을 던져야 함', async () => {
      const reviewId = 1;
      const userId = 1;

      mockReviewsRepository.getReview.mockResolvedValue(null);

      await expect(service.deleteReview(reviewId, userId)).rejects.toThrow(
        NotFoundException,
      );
    });

    it('리뷰 작성자가 아니면 ForbiddenException을 던져야 함', async () => {
      const reviewId = 1;
      const userId = 2;

      const mockReview = {
        id: reviewId,
        userId: 1,
        rating: 5,
        title: '기존 리뷰',
        content: '기존 리뷰 내용',
      };

      mockReviewsRepository.getReview.mockResolvedValue(mockReview);

      await expect(service.deleteReview(reviewId, userId)).rejects.toThrow(
        ForbiddenException,
      );
    });

    it('리뷰 작성자이면 리뷰를 삭제해야 함', async () => {
      const reviewId = 1;
      const userId = 1;

      const mockReview = {
        id: reviewId,
        userId: 1,
        rating: 5,
        title: '기존 리뷰',
        content: '기존 리뷰 내용',
      };

      mockReviewsRepository.getReview.mockResolvedValue(mockReview);
      mockReviewsRepository.deleteReview.mockResolvedValue(undefined);

      await service.deleteReview(reviewId, userId);

      expect(repository.deleteReview).toHaveBeenCalledWith(reviewId);
    });
  });
});
