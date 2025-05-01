import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { Product_Option_Group } from "src/domain/entities";
import ProductOptionGroupRepository from "./Product_Option_Group.repository";

describe("ProductOptionGroupRepository", () => {
  let repository: ProductOptionGroupRepository;
  let mockEntityManager: jest.Mocked<EntityManager>;

  beforeEach(async () => {
    mockEntityManager = {
      save: jest.fn(),
    } as unknown as jest.Mocked<EntityManager>;

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductOptionGroupRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductOptionGroupRepository>(ProductOptionGroupRepository);
  });

  describe("saves", () => {
    it("상품 옵션 그룹 및 옵션 저장 성공", async () => {
      const optionGroups = [
        {
          id: 1,
          name: "색상",
          product_id: 100,
          options: [
            { id: 1, name: "빨강" },
            { id: 2, name: "파랑" },
          ],
        },
      ] as Product_Option_Group[];

      const mockGroupEntity = { id: 1, name: "색상", product: { id: 100 } };
      const mockOptionEntities = [
        { id: 1, name: "빨강", option_group: mockGroupEntity },
        { id: 2, name: "파랑", option_group: mockGroupEntity },
      ];

      mockEntityManager.save
        .mockResolvedValueOnce(mockGroupEntity)
        .mockResolvedValueOnce(mockOptionEntities);

      const result = await repository.saves(optionGroups);

      expect(result).toEqual([mockGroupEntity]);
    });
  });
});
