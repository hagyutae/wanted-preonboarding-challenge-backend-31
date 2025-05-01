import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { CategoryEntity } from "src/infrastructure/entities";
import CategoryRepository from "./Category.repository";

describe("CategoryRepository", () => {
  let repository: CategoryRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CategoryRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<CategoryRepository>(CategoryRepository);
  });

  describe("find_by_filters", () => {
    it("카테고리를 필터로 조회", async () => {
      const categories = [
        { id: 1, name: "Electronics", parent: null },
        { id: 2, name: "Laptops", parent: { id: 1, name: "Electronics" } },
      ];
      mockEntityManager.find = jest.fn().mockResolvedValue(categories);

      const result = await repository.find_by_filters();

      expect(result).toEqual(categories);
      expect(mockEntityManager.find).toHaveBeenCalledWith(CategoryEntity, {
        relations: ["parent"],
      });
    });
  });

  describe("find_by_id", () => {
    it("ID로 카테고리를 조회", async () => {
      const category = { id: 1, name: "Electronics", parent: null };
      mockEntityManager.findOne = jest.fn().mockResolvedValue(category);

      const result = await repository.find_by_id(1);

      expect(result).toEqual(category);
      expect(mockEntityManager.findOne).toHaveBeenCalledWith(CategoryEntity, {
        where: { id: 1 },
        relations: ["parent"],
      });
    });

    it("존재하지 않는 ID로 조회 시 null 반환", async () => {
      mockEntityManager.findOne = jest.fn().mockResolvedValue(null);

      const result = await repository.find_by_id(999);

      expect(result).toBeNull();
      expect(mockEntityManager.findOne).toHaveBeenCalledWith(CategoryEntity, {
        where: { id: 999 },
        relations: ["parent"],
      });
    });
  });
});
