import { EntityManager } from "typeorm";
import { Test, TestingModule } from "@nestjs/testing";

import { CategoryEntity } from "src/infrastructure/entities";
import CategoryService from "./CategoryService";

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

      const result = await service.getAllCategoriesAsTree();

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
});
