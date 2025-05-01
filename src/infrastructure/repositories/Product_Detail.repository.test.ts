import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { Product_Detail } from "src/domain/entities";
import ProductDetailRepository from "./Product_Detail.repository";

describe("ProductDetailRepository", () => {
  let repository: ProductDetailRepository;
  let entityManager: EntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductDetailRepository,
        {
          provide: EntityManager,
          useValue: {
            save: jest.fn(),
            update: jest.fn(),
          },
        },
      ],
    }).compile();

    repository = module.get<ProductDetailRepository>(ProductDetailRepository);
    entityManager = module.get<EntityManager>(EntityManager);
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
      entityManager.save = jest.fn().mockResolvedValue(savedEntity);

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
      jest.spyOn(entityManager, "update").mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(productDetail);

      expect(result).toBe(true);
    });

    it("수정된 행이 없으면 false를 반환", async () => {
      const productDetail = {
        product_id: 1,
        materials: "수정 플라스틱",
        country_of_origin: "수정 한국",
      } as Product_Detail;
      jest.spyOn(entityManager, "update").mockResolvedValue({ affected: 0 } as UpdateResult);

      const result = await repository.update(productDetail);

      expect(result).toBe(false);
    });
  });
});
