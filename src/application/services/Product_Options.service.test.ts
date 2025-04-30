import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { Product_Image, Product_Option } from "src/domain/entities";
import { ProductImageEntity, ProductOptionEntity } from "src/infrastructure/entities";
import ProductOptionsService from "./Product_Options.service";

describe("ProductOptionsService", () => {
  let service: ProductOptionsService;
  let entityManager: jest.Mocked<EntityManager>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductOptionsService,
        {
          provide: EntityManager,
          useValue: {
            create: jest.fn(),
            save: jest.fn(),
            findOne: jest.fn(),
            merge: jest.fn(),
            delete: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<ProductOptionsService>(ProductOptionsService);
    entityManager = module.get(EntityManager);
  });

  describe("addOptions", () => {
    it("새로운 옵션을 생성하고 저장", async () => {
      const id = 1;
      const option_group_id = 10;
      const optionData = { name: "옵션명" } as Product_Option;
      const createdEntity = { id: 123, ...optionData };

      entityManager.create = jest.fn().mockResolvedValue(createdEntity);
      entityManager.save = jest.fn().mockResolvedValue(createdEntity);

      const result = await service.add_options(id, option_group_id, optionData);

      expect(entityManager.create).toHaveBeenCalledWith(ProductOptionEntity, {
        option_group: { id: option_group_id },
        ...optionData,
      });
      expect(result).toEqual(createdEntity);
    });
  });

  describe("updateOptions", () => {
    it("기존 옵션을 찾아 병합하고 저장", async () => {
      const id = 1;
      const option_id = 100;
      const existingOption = { id: option_id, name: "old" } as ProductOptionEntity;
      const updateData = { name: "new" } as Product_Option;
      const mergedEntity = { id: option_id, name: "new" };

      entityManager.findOne = jest.fn().mockResolvedValue(existingOption);
      entityManager.merge = jest.fn().mockReturnValue(mergedEntity);
      entityManager.save = jest.fn().mockResolvedValue(mergedEntity);

      const result = await service.update_options(id, option_id, updateData);

      expect(entityManager.findOne).toHaveBeenCalledWith(ProductOptionEntity, {
        where: { id: option_id },
      });
      expect(entityManager.merge).toHaveBeenCalledWith(
        ProductOptionEntity,
        existingOption,
        updateData,
      );
      expect(entityManager.save).toHaveBeenCalledWith(mergedEntity);
      expect(result).toEqual(mergedEntity);
    });
  });

  describe("deleteOptions", () => {
    it("옵션 삭제", async () => {
      const id = 1;
      const option_id = 200;

      await service.delete_options(id, option_id);

      expect(entityManager.delete).toHaveBeenCalledWith(ProductOptionEntity, option_id);
    });
  });

  describe("addImages", () => {
    it("이미지를 저장", async () => {
      const id = 1;
      const option_id = 2;
      const imageData = { url: "http://image.jpg" } as Product_Image;
      const createdImageEntity = { id: 500, ...imageData };

      entityManager.create = jest.fn().mockResolvedValue(createdImageEntity);
      entityManager.save = jest.fn().mockResolvedValue(createdImageEntity);

      const result = await service.add_images(id, option_id, imageData);

      expect(entityManager.create).toHaveBeenCalledWith(ProductImageEntity, {
        ...imageData,
        option: { id: option_id },
        product: { id },
      });
      expect(result).toEqual(createdImageEntity);
    });
  });
});
