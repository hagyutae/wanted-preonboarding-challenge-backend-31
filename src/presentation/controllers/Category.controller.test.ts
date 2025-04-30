import { Test, TestingModule } from "@nestjs/testing";

import { CategoryService } from "src/application/services";
import { FiltersByCategoryDTO } from "../dto";
import CategoryController from "./Category.controller";

describe("CategoryController", () => {
  let mockController: CategoryController;
  let mockService: jest.Mocked<CategoryService>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [CategoryController],
      providers: [
        {
          provide: CategoryService,
          useValue: {
            getAllCategoriesAsTree: jest.fn(),
            getProductsByCategoryId: jest.fn(),
          },
        },
      ],
    }).compile();

    mockController = module.get<CategoryController>(CategoryController);
    mockService = module.get(CategoryService);
  });

  describe("readCategories", () => {
    it("카테고리 목록 조회 성공", async () => {
      const level = 2;
      const data = [{ id: 1, name: "Category 1" }];
      mockService.getAllCategoriesAsTree = jest.fn().mockResolvedValue(data);

      const result = await mockController.readCategories({ level });

      expect(mockService.getAllCategoriesAsTree).toHaveBeenCalledWith(level);
      expect(result).toEqual({
        success: true,
        data,
        message: "카테고리 목록을 성공적으로 조회했습니다.",
      });
    });
  });

  describe("readProducts", () => {
    it("특정 카테고리의 상품 목록 조회 성공", async () => {
      const id = 1;
      const query = { page: 1, perPage: 10 } as FiltersByCategoryDTO;
      const data = { category: "Category 1" };
      mockService.getProductsByCategoryId = jest.fn().mockResolvedValue(data);

      const result = await mockController.readProducts({ id }, query);

      expect(mockService.getProductsByCategoryId).toHaveBeenCalledWith(id, query);
      expect(result).toEqual({
        success: true,
        data,
        message: "카테고리 상품 목록을 성공적으로 조회했습니다.",
      });
    });
  });
});
