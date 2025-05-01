import { NotFoundException } from "@nestjs/common";
import { Test, TestingModule } from "@nestjs/testing";

import { Review } from "src/domain/entities";
import { IRepository } from "src/domain/repositories";
import { FilterDTO } from "../dto";
import ReviewService from "./Review.service";

describe("ReviewService", () => {
  let service: ReviewService;
  let repository: IRepository<Review>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ReviewService,
        {
          provide: "IReviewRepository",
          useValue: {
            find_by_filters: jest.fn(),
            save: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
            find_by_id: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<ReviewService>(ReviewService);
    repository = module.get<IRepository<Review>>("IReviewRepository");
  });

  it("리뷰 조회", async () => {
    const reviews = [
      { id: 1, product_id: 1, rating: 5, content: "좋아요" },
      { id: 2, product_id: 1, rating: 4, content: "괜찮아요" },
    ];
    repository.find_by_filters = jest.fn().mockResolvedValue(reviews);

    const filter: FilterDTO = { page: 1, per_page: 10, sort: "rating:DESC" };
    const result = await service.find(1, filter);

    expect(result.items).toEqual(reviews);
    expect(result.summary.average_rating).toBe(4.5);
    expect(result.summary.total_count).toBe(2);
    expect(result.summary.distribution[5]).toBe(1);
    expect(result.summary.distribution[4]).toBe(1);
    expect(repository.find_by_filters).toHaveBeenCalledWith({
      product_id: 1,
      page: 1,
      per_page: 10,
      sort_field: "rating",
      sort_order: "DESC",
      rating: undefined,
    });
  });

  it("리뷰 등록", async () => {
    const review = { rating: 5, content: "좋아요" };
    const savedReview = { id: 1, product_id: 1, ...review };
    repository.save = jest.fn().mockResolvedValue(savedReview);

    const result = await service.register(1, review);

    expect(result).toEqual(savedReview);
    expect(repository.save).toHaveBeenCalledWith({ product_id: 1, ...review });
  });

  it("리뷰 수정", async () => {
    const review = { rating: 4, content: "수정된 리뷰" };
    const updatedReview = { id: 1, product_id: 1, ...review };
    repository.update = jest.fn().mockResolvedValue(true);
    repository.find_by_id = jest.fn().mockResolvedValue(updatedReview);

    const result = await service.edit(1, review);

    expect(result).toEqual(updatedReview);
    expect(repository.update).toHaveBeenCalledWith(review, 1);
    expect(repository.find_by_id).toHaveBeenCalledWith(1);
  });

  it("리뷰 수정 실패 시 NotFoundException 발생", async () => {
    repository.update = jest.fn().mockResolvedValue(false);

    await expect(service.edit(1, { rating: 4, content: "수정된 리뷰" })).rejects.toThrow(
      NotFoundException,
    );
    expect(repository.update).toHaveBeenCalledWith({ rating: 4, content: "수정된 리뷰" }, 1);
  });

  it("리뷰 삭제", async () => {
    repository.delete = jest.fn().mockResolvedValue(true);

    await service.remove(1);

    expect(repository.delete).toHaveBeenCalledWith(1);
  });

  it("리뷰 삭제 실패 시 NotFoundException 발생", async () => {
    repository.delete = jest.fn().mockResolvedValue(false);

    await expect(service.remove(1)).rejects.toThrow(NotFoundException);
    expect(repository.delete).toHaveBeenCalledWith(1);
  });
});
