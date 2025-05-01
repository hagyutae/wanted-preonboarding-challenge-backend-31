import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import MainRepository from "./Main.repository";

describe("MainRepository", () => {
  let repository: MainRepository;
  let entityManager: EntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        MainRepository,
        {
          provide: EntityManager,
          useValue: {
            find: jest.fn(),
            getRepository: jest.fn().mockReturnValue({
              createQueryBuilder: jest.fn(),
            }),
          },
        },
      ],
    }).compile();

    repository = module.get<MainRepository>(MainRepository);
    entityManager = module.get<EntityManager>(EntityManager);
  });

  describe("get_new_products", () => {
    it("새로운 상품을 페이지네이션하여 반환", async () => {
      const products = [
        { id: 1, name: "Product 1" },
        { id: 2, name: "Product 2" },
      ];
      entityManager.find = jest.fn().mockResolvedValue(products);

      const result = await repository.get_new_products(1, 10);

      expect(result).toEqual(products);
    });
  });

  describe("get_popular_products", () => {
    it("인기 상품을 반환", async () => {
      const products = [{ id: 1, name: "Product 1", rating: 5 }];
      const queryBuilder = {
        orderBy: jest.fn().mockReturnThis(),
        limit: jest.fn().mockReturnThis(),
        getMany: jest.fn().mockResolvedValue(products),
      };
      entityManager.getRepository = jest.fn().mockReturnValue({
        createQueryBuilder: jest.fn().mockReturnValue(queryBuilder),
      });

      const result = await repository.get_popular_products();

      expect(result).toEqual(products);
      expect(queryBuilder.getMany).toHaveBeenCalled();
    });
  });

  describe("get_featured_categories", () => {
    it("추천 카테고리를 반환", async () => {
      const categories = [
        { id: 1, name: "Category 1", product_count: 10 },
        { id: 2, name: "Category 2", product_count: 5 },
      ];
      const queryBuilder = {
        innerJoinAndSelect: jest.fn().mockReturnThis(),
        select: jest.fn().mockReturnThis(),
        addSelect: jest.fn().mockReturnThis(),
        groupBy: jest.fn().mockReturnThis(),
        orderBy: jest.fn().mockReturnThis(),
        limit: jest.fn().mockReturnThis(),
        getRawMany: jest.fn().mockResolvedValue(categories),
      };
      entityManager.getRepository = jest.fn().mockReturnValue({
        createQueryBuilder: jest.fn().mockReturnValue(queryBuilder),
      });

      const result = await repository.get_featured_categories();

      expect(result).toEqual(categories);
      expect(queryBuilder.getRawMany).toHaveBeenCalled();
    });
  });
});
