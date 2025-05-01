import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { CategoryEntity } from "src/infrastructure/entities";
import CategoryRepository from "./Category.repository";

describe("CategoryRepository", () => {
  let repository: CategoryRepository;
  let entityManager: EntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CategoryRepository,
        {
          provide: EntityManager,
          useValue: {
            find: jest.fn(),
            findOne: jest.fn(),
          },
        },
      ],
    }).compile();

    repository = module.get<CategoryRepository>(CategoryRepository);
    entityManager = module.get<EntityManager>(EntityManager);
  });

  describe("find_by_filters", () => {
    it("카테고리를 필터로 조회", async () => {
      const categories = [
        { id: 1, name: "Electronics", parent: null },
        { id: 2, name: "Laptops", parent: { id: 1, name: "Electronics" } },
      ];
      jest.spyOn(entityManager, "find").mockResolvedValue(categories);

      const result = await repository.find_by_filters();

      expect(result).toEqual(categories);
      expect(entityManager.find).toHaveBeenCalledWith(CategoryEntity, {
        relations: ["parent"],
      });
    });
  });

  describe("find_by_id", () => {
    it("ID로 카테고리를 조회", async () => {
      const category = { id: 1, name: "Electronics", parent: null };
      jest.spyOn(entityManager, "findOne").mockResolvedValue(category);

      const result = await repository.find_by_id(1);

      expect(result).toEqual(category);
      expect(entityManager.findOne).toHaveBeenCalledWith(CategoryEntity, {
        where: { id: 1 },
        relations: ["parent"],
      });
    });

    it("존재하지 않는 ID로 조회 시 null 반환", async () => {
      jest.spyOn(entityManager, "findOne").mockResolvedValue(null);

      const result = await repository.find_by_id(999);

      expect(result).toBeNull();
      expect(entityManager.findOne).toHaveBeenCalledWith(CategoryEntity, {
        where: { id: 999 },
        relations: ["parent"],
      });
    });
  });
});
