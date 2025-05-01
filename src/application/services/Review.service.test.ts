import { NotFoundException } from "@nestjs/common";
import { Test, TestingModule } from "@nestjs/testing";

import { FilterDTO } from "../dto";
import ReviewService from "./Review.service";

describe("ReviewService", () => {
  let service: ReviewService;
  const mockRepository = global.mockReviewRepository;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ReviewService, ...global.mockRepositoryProviders],
    }).compile();

    service = module.get<ReviewService>(ReviewService);
  });

  it("리뷰 조회", async () => {
    const reviews = [
      { id: 1, product_id: 1, rating: 5, content: "좋아요" },
      { id: 2, product_id: 1, rating: 4, content: "괜찮아요" },
    ];
    mockRepository.find_by_filters = jest.fn().mockResolvedValue(reviews);

    const filter: FilterDTO = { page: 1, per_page: 10, sort: "rating:DESC" };
    const result = await service.find(1, filter);

    expect(result.items).toEqual(reviews);
    expect(result.summary.average_rating).toBe(4.5);
    expect(result.summary.total_count).toBe(2);
    expect(result.summary.distribution[5]).toBe(1);
    expect(result.summary.distribution[4]).toBe(1);
    expect(mockRepository.find_by_filters).toHaveBeenCalledWith({
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
    mockRepository.save = jest.fn().mockResolvedValue(savedReview);

    const result = await service.register(1, review);

    expect(result).toEqual(savedReview);
    expect(mockRepository.save).toHaveBeenCalledWith({ product_id: 1, ...review });
  });

  it("리뷰 수정", async () => {
    const review = { rating: 4, content: "수정된 리뷰" };
    const updatedReview = { id: 1, product_id: 1, ...review };
    mockRepository.update = jest.fn().mockResolvedValue(true);
    mockRepository.find_by_id = jest.fn().mockResolvedValue(updatedReview);

    const result = await service.edit(1, review);

    expect(result).toEqual(updatedReview);
    expect(mockRepository.update).toHaveBeenCalledWith(review, 1);
    expect(mockRepository.find_by_id).toHaveBeenCalledWith(1);
  });

  it("리뷰 수정 실패 시 NotFoundException 발생", async () => {
    mockRepository.update = jest.fn().mockResolvedValue(false);

    await expect(service.edit(1, { rating: 4, content: "수정된 리뷰" })).rejects.toThrow(
      NotFoundException,
    );
    expect(mockRepository.update).toHaveBeenCalledWith({ rating: 4, content: "수정된 리뷰" }, 1);
  });

  it("리뷰 삭제", async () => {
    mockRepository.delete = jest.fn().mockResolvedValue(true);

    await service.remove(1);

    expect(mockRepository.delete).toHaveBeenCalledWith(1);
  });

  it("리뷰 삭제 실패 시 NotFoundException 발생", async () => {
    mockRepository.delete = jest.fn().mockResolvedValue(false);

    await expect(service.remove(1)).rejects.toThrow(NotFoundException);
    expect(mockRepository.delete).toHaveBeenCalledWith(1);
  });
});
