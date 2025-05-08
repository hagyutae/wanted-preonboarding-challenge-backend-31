import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { Product_Image } from "src/product/domain/entities";
import ProductImageRepository from "./Product_Image.repository";

describe("ProductImageRepository", () => {
  let repository: ProductImageRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductImageRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductImageRepository>(ProductImageRepository);
  });

  describe("save", () => {
    it("이미지 저장 성공", async () => {
      const productImage = {
        id: 1,
        product_id: 1,
        option_id: 2,
        url: "http://example.com/image.jpg",
      } as Product_Image;
      const mockSavedEntity = { id: 1, ...productImage };
      mockEntityManager.save.mockResolvedValue(mockSavedEntity);

      const result = await repository.save(productImage);

      expect(result).toEqual(mockSavedEntity);
    });
  });

  describe("saves", () => {
    it("여러 이미지 저장 성공", async () => {
      const productImages = [
        { id: 1, product_id: 1, option_id: 2, url: "http://example.com/image1.jpg" },
        { id: 2, product_id: 1, option_id: null, url: "http://example.com/image2.jpg" },
      ] as Product_Image[];
      const mockSavedEntities = productImages.map((image) => ({ id: image.id, ...image }));
      mockEntityManager.save.mockResolvedValue(mockSavedEntities);

      const result = await repository.saves(productImages);

      expect(result).toEqual(mockSavedEntities);
    });
  });
});
