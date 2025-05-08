import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { Product_Price } from "src/product/domain/entities";
import ProductPriceRepository from "./Product_Price.repository";

describe("ProductPriceRepository", () => {
  let repository: ProductPriceRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductPriceRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductPriceRepository>(ProductPriceRepository);
  });

  describe("save", () => {
    it("상품 가격 저장 성공", async () => {
      const productPrice = { id: 1, product_id: 100, base_price: 2000 } as Product_Price;
      const mockSavedEntity = { id: 1, price: 2000, product: { id: 100 } };

      mockEntityManager.save.mockResolvedValueOnce(mockSavedEntity);

      const result = await repository.save(productPrice);

      expect(result).toEqual(mockSavedEntity);
    });
  });

  describe("update", () => {
    it("상품 가격 업데이트 성공", async () => {
      const productPrice = { id: 1, product_id: 100, base_price: 3000 } as Product_Price;
      mockEntityManager.update.mockResolvedValueOnce({ affected: 1 } as UpdateResult);

      const result = await repository.update(productPrice);

      expect(result).toBe(true);
    });

    it("상품 가격 업데이트 실패", async () => {
      const productPrice = { id: 1, product_id: 100, base_price: 3000 } as Product_Price;
      mockEntityManager.update.mockResolvedValueOnce({ affected: 0 } as UpdateResult);

      const result = await repository.update(productPrice);

      expect(result).toBe(false);
    });
  });
});
