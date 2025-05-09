import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import BrowsingRepository from "./Browsing.repository";

describe("BrowsingRepository", () => {
  let repository: BrowsingRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        BrowsingRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<BrowsingRepository>(BrowsingRepository);
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
