import { Test, TestingModule } from "@nestjs/testing";

import { MainService } from "src/application/services";
import { CategoryEntity } from "src/infrastructure/entities";
import { ProductSummaryView } from "src/infrastructure/views";
import { ResponseDTO } from "../dto";
import MainController from "./Main.controller";

describe("MainController", () => {
  let controller: MainController;
  let mockMainService: jest.Mocked<MainService>;

  beforeEach(async () => {
    mockMainService = {
      getNewProducts: jest.fn(),
      getPopularProducts: jest.fn(),
      getFeaturedCategories: jest.fn(),
    } as unknown as jest.Mocked<MainService>;

    const module: TestingModule = await Test.createTestingModule({
      controllers: [MainController],
      providers: [
        {
          provide: MainService,
          useValue: mockMainService,
        },
      ],
    }).compile();

    controller = module.get<MainController>(MainController);
  });

  describe("getMainProducts", () => {
    it("메인 페이지용 상품 목록 조회 성공", async () => {
      const mockNewProducts = [{ id: 1, name: "새 상품" }] as ProductSummaryView[];
      const mockPopularProducts = [{ id: 2, name: "인기 상품" }] as ProductSummaryView[];
      const mockFeaturedCategories = [{ id: 3, name: "추천 카테고리" }] as CategoryEntity[];

      mockMainService.get_new_products.mockResolvedValue(mockNewProducts);
      mockMainService.get_popular_products.mockResolvedValue(mockPopularProducts);
      mockMainService.get_featured_categories.mockResolvedValue(mockFeaturedCategories);

      const result: ResponseDTO = await controller.read_main_products();

      expect(mockMainService.get_new_products).toHaveBeenCalled();
      expect(mockMainService.get_popular_products).toHaveBeenCalled();
      expect(mockMainService.get_featured_categories).toHaveBeenCalled();
      expect(result).toEqual({
        success: true,
        data: {
          new_products: mockNewProducts,
          popular_products: mockPopularProducts,
          featured_categories: mockFeaturedCategories,
        },
        message: "메인 페이지 상품 목록을 성공적으로 조회했습니다.",
      });
    });
  });
});
