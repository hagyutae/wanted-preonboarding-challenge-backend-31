import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { Product_Detail } from "@product/domain/entities";
import ProductDetailRepository from "./Product_Detail.repository";

describe("ProductDetailRepository", () => {
  let repository: ProductDetailRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductDetailRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductDetailRepository>(ProductDetailRepository);
  });

  describe("save", () => {
    it("상품 상세 정보 저장", async () => {
      const productDetail = {
        product_id: 1,
        materials: "플라스틱",
        country_of_origin: "한국",
      } as Product_Detail;
      const savedEntity = {
        id: 1,
        ...productDetail,
        product: { id: productDetail.product_id },
      };
      mockEntityManager.save = jest.fn().mockResolvedValue(savedEntity);

      const result = await repository.save(productDetail);

      expect(result).toEqual(savedEntity);
    });
  });

  describe("update", () => {
    it("상품 상세 정보 수정한", async () => {
      const productDetail = {
        product_id: 1,
        materials: "플라스틱",
        country_of_origin: "한국",
      } as Product_Detail;
      mockEntityManager.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(productDetail);

      expect(result).toBe(true);
    });

    it("수정된 행이 없으면 false를 반환", async () => {
      const productDetail = {
        product_id: 1,
        materials: "수정 플라스틱",
        country_of_origin: "수정 한국",
      } as Product_Detail;
      mockEntityManager.update = jest.fn().mockResolvedValue({ affected: 0 } as UpdateResult);

      const result = await repository.update(productDetail);

      expect(result).toBe(false);
    });
  });
});
