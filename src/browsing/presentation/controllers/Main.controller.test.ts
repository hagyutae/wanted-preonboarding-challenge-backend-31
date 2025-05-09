import { Test, TestingModule } from "@nestjs/testing";

import { CategoryEntity } from "@category/infrastructure/entities";
import { BrowsingService } from "@browsing/application/services";
import { ProductSummaryView } from "@browsing/infrastructure/views";
import { ResponseDTO } from "../dto";
import MainController from "./Main.controller";

describe("MainController", () => {
  let controller: MainController;
  let mockService: jest.Mocked<BrowsingService>;

  beforeEach(async () => {
    mockService = {
      find: jest.fn(),
    } as unknown as jest.Mocked<BrowsingService>;

    const module: TestingModule = await Test.createTestingModule({
      controllers: [MainController],
      providers: [
        {
          provide: BrowsingService,
          useValue: mockService,
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

      mockService.find.mockResolvedValue({
        new_products: mockNewProducts,
        popular_products: mockPopularProducts,
        featured_categories: mockFeaturedCategories,
      });

      const result: ResponseDTO<any> = await controller.read_main_products();

      expect(mockService.find).toHaveBeenCalled();
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
