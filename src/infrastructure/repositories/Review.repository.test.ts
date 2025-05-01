import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { Review } from "src/domain/entities";
import ReviewRepository from "./Review.repository";

describe("ReviewRepository", () => {
  let repository: ReviewRepository;
  let mockEntityManager: jest.Mocked<EntityManager>;

  beforeEach(async () => {
    mockEntityManager = {
      create: jest.fn(),
      save: jest.fn(),
      getRepository: jest.fn().mockReturnValue({
        createQueryBuilder: jest.fn(),
      }),
      update: jest.fn(),
      delete: jest.fn(),
    } as unknown as jest.Mocked<EntityManager>;

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ReviewRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ReviewRepository>(ReviewRepository);
  });

  describe("save", () => {
    it("리뷰 저장 성공", async () => {
      const review = {
        id: 1,
        product_id: 100,
        user_id: 10,
        rating: 5,
        comment: "좋아요!",
      } as Review;

      mockEntityManager.save.mockResolvedValue(review);

      const result = await repository.save(review);

      expect(result).toEqual(review);
    });
  });

  describe("find_by_filters", () => {
    it("필터를 사용한 리뷰 조회 성공", async () => {
      const mockQueryBuilder = {
        leftJoinAndSelect: jest.fn().mockReturnThis(),
        where: jest.fn().mockReturnThis(),
        andWhere: jest.fn().mockReturnThis(),
        orderBy: jest.fn().mockReturnThis(),
        skip: jest.fn().mockReturnThis(),
        take: jest.fn().mockReturnThis(),
        getMany: jest.fn().mockResolvedValue([{ id: 1, rating: 5, content: "좋아요!" }]),
      };

      mockEntityManager.getRepository("repo").createQueryBuilder = jest
        .fn()
        .mockReturnValue(mockQueryBuilder);

      const result = await repository.find_by_filters({
        product_id: 100,
        page: 1,
        per_page: 10,
        sort_field: "rating",
        sort_order: "DESC",
        rating: 5,
      });

      expect(result).toEqual([{ id: 1, rating: 5, content: "좋아요!" }]);
    });
  });

  describe("update", () => {
    it("리뷰 업데이트 성공", async () => {
      mockEntityManager.update.mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update({ title: "수정된 리뷰" } as Review, 1);

      expect(result).toBe(true);
    });

    it("리뷰 업데이트 실패", async () => {
      mockEntityManager.update.mockResolvedValue({ affected: 0 } as UpdateResult);

      const result = await repository.update({ title: "수정된 리뷰" } as Review, 1);

      expect(result).toBe(false);
    });
  });

  describe("delete", () => {
    it("리뷰 삭제 성공", async () => {
      mockEntityManager.delete.mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(1);

      expect(result).toBe(true);
    });

    it("리뷰 삭제 실패", async () => {
      mockEntityManager.delete.mockResolvedValue({ affected: 0 } as UpdateResult);

      const result = await repository.delete(1);

      expect(result).toBe(false);
    });
  });
});
