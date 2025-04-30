import { EntityManager } from "typeorm";
import { Test, TestingModule } from "@nestjs/testing";

import { ReviewEntity } from "src/infrastructure/entities";
import ReviewService from "./Review.service";

describe("ReviewService", () => {
  let service: ReviewService;
  let mockEntityManager: jest.Mocked<EntityManager>;

  beforeEach(async () => {
    mockEntityManager = {
      getRepository: jest.fn(),
      create: jest.fn(),
      save: jest.fn(),
      update: jest.fn(),
      findOne: jest.fn(),
      delete: jest.fn(),
    } as unknown as jest.Mocked<EntityManager>;

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ReviewService,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    service = module.get<ReviewService>(ReviewService);
  });

  describe("get", () => {
    it("리뷰 목록 가져오기 성공", async () => {
      const product_id = 1;
      const reviews = [
        { id: 1, rating: 5, created_at: new Date() },
        { id: 2, rating: 4, created_at: new Date() },
      ];
      const mockQueryBuilder = {
        where: jest.fn().mockReturnThis(),
        andWhere: jest.fn().mockReturnThis(),
        orderBy: jest.fn().mockReturnThis(),
        skip: jest.fn().mockReturnThis(),
        take: jest.fn().mockReturnThis(),
        getMany: jest.fn().mockResolvedValue(reviews),
      };

      const mockRepository = {
        createQueryBuilder: jest.fn().mockReturnValue(mockQueryBuilder),
      };

      mockEntityManager.getRepository = jest.fn().mockReturnValue(mockRepository);

      const result = await service.get(product_id, { page: 1, perPage: 10 });

      expect(mockQueryBuilder.where).toHaveBeenCalledWith("1 = 1");
      expect(mockQueryBuilder.andWhere).toHaveBeenCalledWith("reviews.product_id = :product_id", {
        product_id,
      });
      expect(result.items).toEqual(reviews);
      expect(result.summary.average_rating).toBe(4.5);
      expect(result.pagination.current_page).toBe(1);
    });
  });

  describe("create", () => {
    it("리뷰를 생성", async () => {
      const productId = 1;
      const reviewInput = {
        title: "좋은 상품입니다",
        content: "정말 마음에 들어요",
        rating: 5,
      };

      const mockReviewEntity = {
        id: 1,
        ...reviewInput,
        product: { id: productId },
      };

      mockEntityManager.create = jest.fn().mockReturnValue(mockReviewEntity);
      mockEntityManager.save = jest.fn().mockResolvedValue(mockReviewEntity);

      await expect(service.create(productId, reviewInput)).resolves.toEqual(mockReviewEntity);

      expect(mockEntityManager.create).toHaveBeenCalledWith(ReviewEntity, {
        ...reviewInput,
        product: { id: productId },
      });
      expect(mockEntityManager.save).toHaveBeenCalledWith(mockReviewEntity);
    });
  });

  describe("update", () => {
    it("리뷰 수정 성공", async () => {
      const id = 1;
      const review = { rating: 4, content: "Updated review" };
      const updatedReview = { id, ...review };
      mockEntityManager.update = jest.fn().mockResolvedValue(undefined);
      mockEntityManager.findOne = jest.fn().mockResolvedValue(updatedReview);

      const result = await service.update(id, review);

      expect(mockEntityManager.update).toHaveBeenCalledWith(ReviewEntity, id, review);
      expect(mockEntityManager.findOne).toHaveBeenCalledWith(ReviewEntity, { where: { id } });
      expect(result).toEqual(updatedReview);
    });
  });

  describe("delete", () => {
    it("리뷰 삭제 성공", async () => {
      const id = 1;
      mockEntityManager.delete = jest.fn().mockResolvedValue({ affected: 1 });

      await service.delete(id);

      expect(mockEntityManager.delete).toHaveBeenCalledWith(ReviewEntity, id);
    });
  });
});
