import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { ProductTagDTO } from "src/application/dto";
import ProductTagRepository from "./Product_Tag.repository";

describe("ProductTagRepository", () => {
  let repository: ProductTagRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductTagRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductTagRepository>(ProductTagRepository);
  });

  describe("saves", () => {
    it("상품 태그 저장 성공", async () => {
      const productTags: ProductTagDTO[] = [
        { tag_id: 1, product_id: 100 },
        { tag_id: 2, product_id: 100 },
      ];

      const mockSavedEntities = [
        { id: 1, product: { id: 100 }, tag: { id: 1 } },
        { id: 2, product: { id: 100 }, tag: { id: 2 } },
      ];

      mockEntityManager.save.mockResolvedValue(mockSavedEntities);

      const result = await repository.saves(productTags);

      expect(result).toEqual(mockSavedEntities);
    });
  });
});
