import { Test, TestingModule } from "@nestjs/testing";

import { ReviewService } from "src/product/application/services";
import { ParamDTO, ResponseDTO, ReviewBodyDTO, ReviewQueryDTO } from "../dto";
import ReviewController from "./Review.controller";

describe("ReviewController", () => {
  let controller: ReviewController;
  let mockReviewService: jest.Mocked<ReviewService>;

  beforeEach(async () => {
    mockReviewService = {
      get: jest.fn(),
      create: jest.fn(),
      update: jest.fn(),
      delete: jest.fn(),
    } as unknown as jest.Mocked<ReviewService>;

    const module: TestingModule = await Test.createTestingModule({
      controllers: [ReviewController],
      providers: [
        {
          provide: ReviewService,
          useValue: mockReviewService,
        },
      ],
    }).compile();

    controller = module.get<ReviewController>(ReviewController);
  });

  describe("read", () => {
    it("상품 리뷰 조회 성공", async () => {
      const id = 1;
      const query: ReviewQueryDTO = { page: 1, perPage: 10 };
      const mockData = { items: [], summary: {}, pagination: {} };
      mockReviewService.find = jest.fn().mockResolvedValue(mockData);

      const result: ResponseDTO<any> = await controller.read({ id } as ParamDTO, query);

      expect(mockReviewService.find).toHaveBeenCalledWith(id, query);
      expect(result).toEqual({
        success: true,
        data: mockData,
        message: "상품 리뷰를 성공적으로 조회했습니다.",
      });
    });
  });

  describe("create", () => {
    it("리뷰 작성 성공", async () => {
      const id = 1;
      const body: ReviewBodyDTO = { title: "좋은 상품", content: "만족합니다", rating: 5 };
      const mockData = { id: 1, ...body };
      mockReviewService.register = jest.fn().mockResolvedValue(mockData);

      const result: ResponseDTO<any> = await controller.create({ id } as ParamDTO, body);

      expect(mockReviewService.register).toHaveBeenCalledWith(id, body);
      expect(result).toEqual({
        success: true,
        data: mockData,
        message: "리뷰가 성공적으로 작성되었습니다.",
      });
    });
  });

  describe("update", () => {
    it("리뷰 수정 성공", async () => {
      const id = 1;
      const body: ReviewBodyDTO = { title: "수정된 제목", content: "수정된 내용", rating: 4 };
      const mockData = { id, ...body };
      mockReviewService.edit = jest.fn().mockResolvedValue(mockData);

      const result: ResponseDTO<any> = await controller.update({ id } as ParamDTO, body);

      expect(mockReviewService.edit).toHaveBeenCalledWith(id, body);
      expect(result).toEqual({
        success: true,
        data: mockData,
        message: "리뷰가 성공적으로 수정되었습니다.",
      });
    });
  });

  describe("delete", () => {
    it("리뷰 삭제 성공", async () => {
      const id = 1;
      mockReviewService.remove = jest.fn().mockResolvedValue(true);

      const result: ResponseDTO<any> = await controller.delete({ id } as ParamDTO);

      expect(mockReviewService.remove).toHaveBeenCalledWith(id);
      expect(result).toEqual({
        success: true,
        data: null,
        message: "리뷰가 성공적으로 삭제되었습니다.",
      });
    });
  });
});
