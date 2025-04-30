import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { Product_Category, Product_Image, Product_Option_Group } from "src/domain/entities";
import { ProductEntity } from "src/infrastructure/entities";
import { ProductInputDTO } from "../dto";
import ProductService from "./Product.service";

describe("ProductService", () => {
  let service: ProductService;
  let entityManager: EntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        {
          provide: EntityManager,
          useValue: {
            findOne: jest.fn(),
            create: jest.fn(),
            save: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
            getRepository: jest.fn().mockReturnValue({
              createQueryBuilder: jest.fn().mockReturnValue({
                leftJoinAndSelect: jest.fn().mockReturnThis(),
                where: jest.fn().mockReturnThis(),
                andWhere: jest.fn().mockReturnThis(),
                orderBy: jest.fn().mockReturnThis(),
                skip: jest.fn().mockReturnThis(),
                take: jest.fn().mockReturnThis(),
                offset: jest.fn().mockReturnThis(),
                limit: jest.fn().mockReturnThis(),
                getMany: jest.fn(),
              }),
            }),
          },
        },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
    entityManager = module.get<EntityManager>(EntityManager);
  });

  describe("create", () => {
    it("상품 생성", async () => {
      const mockProductInput = { name: "Product Name" } as ProductInputDTO;
      const mockSavedProduct = { id: 1, ...mockProductInput };

      entityManager.findOne = jest
        .fn()
        .mockResolvedValueOnce({ id: 1, name: "Seller" })
        .mockResolvedValueOnce({ id: 1, name: "Brand" });
      entityManager.create = jest.fn().mockReturnValue(mockSavedProduct);
      entityManager.save = jest.fn().mockResolvedValue(mockSavedProduct);

      await expect(service.register(mockProductInput)).resolves.toEqual(mockSavedProduct);
    });
  });

  describe("getAll", () => {
    it("페이지네이션된 상품들을 반환", async () => {
      const mockProducts = [
        { id: 1, name: "Product 1" },
        { id: 2, name: "Product 2" },
      ];
      (
        entityManager.getRepository(ProductEntity).createQueryBuilder().getMany as jest.Mock
      ).mockResolvedValue(mockProducts);

      const result = await service.find_all({ page: 1, per_page: 10 });

      expect(result.items).toEqual(mockProducts);
      expect(result.pagination).toEqual({
        total_items: mockProducts.length,
        total_pages: 1,
        current_page: 1,
        per_page: 10,
      });
    });
  });

  describe("getById", () => {
    it("id로 상품을 반환", async () => {
      const mockProduct = { id: 1, name: "Product 1" };
      entityManager.findOne = jest.fn().mockResolvedValue(mockProduct);

      const result = await service.find(1);
      expect(result).toEqual(mockProduct);
    });
  });

  describe("update", () => {
    it("상품을 업데이트", async () => {
      const mockProduct = { id: 1, name: "Updated Product" };
      const mockProductInput = { name: "Product Name" } as ProductInputDTO;
      entityManager.update = jest.fn().mockResolvedValue({});
      entityManager.findOne = jest.fn().mockResolvedValue(mockProduct);

      const result = await service.edit(1, mockProductInput);

      expect(result).toEqual(mockProduct);
      expect(entityManager.update).toHaveBeenCalledWith(ProductEntity, 1, mockProductInput);
    });
  });

  describe("delete", () => {
    it("상품 삭제", async () => {
      entityManager.delete = jest.fn().mockResolvedValue({ affected: 1 });

      await expect(service.remove(1)).resolves.not.toThrow();
      expect(entityManager.delete).toHaveBeenCalledWith(ProductEntity, 1);
    });
  });
});
