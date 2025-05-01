import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { Product_Category } from "src/domain/entities";
import ProductCategoryRepository from "./Product_Category.repository";

describe("ProductCategoryRepository", () => {
  let repository: ProductCategoryRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductCategoryRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductCategoryRepository>(ProductCategoryRepository);
  });

  describe("saves", () => {
    it("카테고리 저장", async () => {
      const categories: Product_Category[] = [
        { product_id: 1, category_id: 2, is_primary: true },
        { product_id: 1, category_id: 3, is_primary: false },
      ];
      const savedEntities = [
        { id: 1, product: { id: 1 }, category: { id: 2 }, is_primary: true },
        { id: 2, product: { id: 1 }, category: { id: 3 }, is_primary: false },
      ];
      mockEntityManager.save = jest.fn().mockResolvedValue(savedEntities);

      const result = await repository.saves(categories);

      expect(result).toEqual(savedEntities);
    });
  });

  describe("update", () => {
    it("카테고리 업데이트 성공", async () => {
      const category: Product_Category = {
        product_id: 1,
        category_id: 2,
        is_primary: true,
      };
      mockEntityManager.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(category);

      expect(result).toBe(true);
    });

    it("카테고리 업데이트 실패", async () => {
      const category: Product_Category = {
        product_id: 1,
        category_id: 2,
        is_primary: true,
      };
      mockEntityManager.update = jest.fn().mockResolvedValue({ affected: 0 } as UpdateResult);

      const result = await repository.update(category);

      expect(result).toBe(false);
    });
  });
});
