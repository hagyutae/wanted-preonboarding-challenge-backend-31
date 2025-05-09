import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import MainRepository from "./Main.repository";

describe("MainRepository", () => {
  let repository: MainRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        MainRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<MainRepository>(MainRepository);
  });

  describe("get_new_products", () => {
    it("새로운 상품을 페이지네이션하여 반환", async () => {
      const products = [
        { id: 1, name: "Product 1" },
        { id: 2, name: "Product 2" },
      ];
      mockEntityManager.find = jest.fn().mockResolvedValue(products);

      const result = await repository.get_new_products(1, 10);

      expect(result).toEqual(products);
    });
  });

  describe("get_popular_products", () => {
    it("인기 상품을 반환", async () => {
      const products = [{ id: 1, name: "Product 1", rating: 5 }];
      mockEntityManager.getRepository().createQueryBuilder().getMany = jest
        .fn()
        .mockResolvedValue(products);

      const result = await repository.get_popular_products();

      expect(result).toEqual(products);
    });
  });

  describe("get_featured_categories", () => {
    it("추천 카테고리를 반환", async () => {
      const categories = [
        { id: 1, name: "Category 1", product_count: 10 },
        { id: 2, name: "Category 2", product_count: 5 },
      ];
      mockEntityManager.createQueryBuilder().getRawMany = jest.fn().mockResolvedValue(categories);

      const result = await repository.get_featured_categories();

      expect(result).toEqual(categories);
    });
  });
});
