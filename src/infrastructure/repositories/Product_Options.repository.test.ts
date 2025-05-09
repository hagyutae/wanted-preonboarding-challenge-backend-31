import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { Product_Option } from "src/domain/entities";
import ProductOptionsRepository from "./Product_Options.repository";

describe("ProductOptionsRepository", () => {
  let repository: ProductOptionsRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductOptionsRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductOptionsRepository>(ProductOptionsRepository);
  });

  describe("save", () => {
    it("상품 옵션 저장 성공", async () => {
      const option = { id: 1, name: "빨강", option_group_id: 10 } as Product_Option;
      const mockSavedEntity = { id: 1, name: "빨강", option_group: { id: 10 } };

      mockEntityManager.save.mockResolvedValue(mockSavedEntity);

      const result = await repository.save(option);

      expect(result).toEqual(mockSavedEntity);
    });
  });

  describe("update", () => {
    it("상품 옵션 업데이트 성공", async () => {
      const option = { name: "파랑" } as Product_Option;
      const optionId = 1;
      const mockUpdatedEntity = { id: 1, name: "파랑" };

      mockEntityManager.save.mockResolvedValue(mockUpdatedEntity);

      const result = await repository.update(option, optionId);

      expect(result).toEqual(mockUpdatedEntity);
    });
  });

  describe("delete", () => {
    it("상품 옵션 삭제 성공", async () => {
      const optionId = 1;

      mockEntityManager.delete.mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(optionId);

      expect(result).toBe(true);
    });

    it("상품 옵션 삭제 실패", async () => {
      const optionId = 1;

      mockEntityManager.delete.mockResolvedValue({ affected: 0 } as UpdateResult);

      const result = await repository.delete(optionId);

      expect(result).toBe(false);
    });
  });
});
