import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { CategoryEntity } from "src/infrastructure/entities";
import CategoryService from "./Category.service";

describe("CategoryService", () => {
  let service: CategoryService;
  let mockEntityManager: jest.Mocked<EntityManager>;

  beforeEach(async () => {
    mockEntityManager = {
      find: jest.fn(),
    } as unknown as jest.Mocked<EntityManager>;

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CategoryService,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    service = module.get<CategoryService>(CategoryService);
  });

  describe("buildTree", () => {
    it("트리 구조 생성", () => {
      const mockCategories = [
        { id: 1, name: "대분류1" },
        { id: 2, name: "중분류1", parent: { id: 1 } },
        { id: 3, name: "소분류1", parent: { id: 2 } },
      ] as CategoryEntity[];

      const result = (service as any).buildTree(mockCategories, 1);

      expect(result).toEqual([
        {
          id: 1,
          name: "대분류1",
          children: [
            {
              id: 2,
              name: "중분류1",
              parent: { id: 1 },
              children: [
                {
                  id: 3,
                  name: "소분류1",
                  parent: { id: 2 },
                  children: [],
                },
              ],
            },
          ],
        },
      ]);
    });

    it("레벨이 3을 초과하면 빈 배열 반환", () => {
      const mockCategories = [
        { id: 1, name: "대분류1" },
        { id: 2, name: "중분류1", parent: { id: 1 } },
        { id: 3, name: "소분류1", parent: { id: 2 } },
      ] as CategoryEntity[];

      const result = (service as any).buildTree(mockCategories, 4);

      expect(result).toEqual([]);
    });
  });

  describe("getAllCategoriesAsTree", () => {
    it("카테고리를 트리 구조로 반환", async () => {
      const mockCategories = [
        { id: 1, name: "대분류1" },
        { id: 2, name: "중분류1", parent: { id: 1 } },
        { id: 3, name: "소분류1", parent: { id: 2 } },
      ] as CategoryEntity[];

      mockEntityManager.find.mockResolvedValue(mockCategories);

      const result = await service.find_all_as_tre();

      expect(mockEntityManager.find).toHaveBeenCalledWith(CategoryEntity, {
        relations: ["parent"],
      });
      expect(result).toEqual([
        {
          id: 1,
          name: "대분류1",
          children: [
            {
              id: 2,
              name: "중분류1",
              parent: { id: 1 },
              children: [
                {
                  id: 3,
                  name: "소분류1",
                  parent: { id: 2 },
                  children: [],
                },
              ],
            },
          ],
        },
      ]);
    });
  });

  describe("getProductsByCategoryId", () => {
    it("카테고리 ID로 제품을 페이징 처리하여 반환", async () => {
      const mockCategory = { id: 1, name: "대분류1" } as CategoryEntity;
      const mockProducts = [
        { id: 1, name: "제품1", created_at: new Date() },
        { id: 2, name: "제품2", created_at: new Date() },
      ];

      mockEntityManager.findOne = jest.fn().mockResolvedValue(mockCategory);
      mockEntityManager.getRepository = jest.fn().mockReturnValue({
        createQueryBuilder: jest.fn().mockReturnValue({
          where: jest.fn().mockReturnThis(),
          andWhere: jest.fn().mockReturnThis(),
          orderBy: jest.fn().mockReturnThis(),
          skip: jest.fn().mockReturnThis(),
          take: jest.fn().mockReturnThis(),
          getMany: jest.fn().mockResolvedValue(mockProducts),
        }),
      });

      const result = await service.find_products_by_category_id(1, {
        page: 1,
        per_page: 2,
        sort: "created_at:desc",
        has_sub: true,
      });

      expect(mockEntityManager.findOne).toHaveBeenCalledWith(CategoryEntity, {
        where: { id: 1 },
        relations: ["parent"],
      });
      expect(mockEntityManager.getRepository).toHaveBeenCalledWith(CategoryEntity);
      expect(result).toEqual({
        category: mockCategory,
        items: mockProducts,
        pagination: {
          total_items: mockProducts.length,
          total_pages: 1,
          current_page: 1,
          per_page: 2,
        },
      });
    });
  });
});
