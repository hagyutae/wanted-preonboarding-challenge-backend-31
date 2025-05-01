import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { Product, Product_Catalog, Product_Summary } from "src/domain/entities";
import ProductRepository from "./Product.repository";

describe("ProductRepository", () => {
  let repository: ProductRepository;
  let mockEntityManager: jest.Mocked<EntityManager>;

  beforeEach(async () => {
    mockEntityManager = {
      save: jest.fn(),
      findOne: jest.fn(),
      update: jest.fn(),
      delete: jest.fn(),
      getRepository: jest.fn().mockReturnThis(),
      createQueryBuilder: jest.fn().mockReturnValue({
        subQuery: jest.fn().mockReturnThis(),
        select: jest.fn().mockReturnThis(),
        from: jest.fn().mockReturnThis(),
        leftJoin: jest.fn().mockReturnThis(),
        where: jest.fn().mockReturnThis(),
        getQuery: jest.fn().mockReturnValue("mockInnerQuery"),
        getRepository: jest.fn().mockReturnThis(),
        createQueryBuilder: jest.fn().mockReturnThis(),
        andWhere: jest.fn().mockReturnThis(),
        orderBy: jest.fn().mockReturnThis(),
        offset: jest.fn().mockReturnThis(),
        limit: jest.fn().mockReturnThis(),
        setParameter: jest.fn().mockReturnThis(),
        getMany: jest.fn(),
      }),
    } as unknown as jest.Mocked<EntityManager>;

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    repository = module.get<ProductRepository>(ProductRepository);
  });

  describe("save", () => {
    it("상품 저장 성공", async () => {
      const product = {
        id: 1,
        name: "Test Product",
        seller_id: 10,
        brand_id: 20,
      } as Product;

      const mockSavedProduct = { ...product, seller: { id: 10 }, brand: { id: 20 } };
      mockEntityManager.save.mockResolvedValue(mockSavedProduct);

      const result = await repository.save(product);

      expect(result).toEqual(mockSavedProduct);
    });
  });

  describe("find_by_filters", () => {
    it("필터로 상품 검색 성공", async () => {
      const filters = {
        page: 1,
        per_page: 10,
        sort_field: "name",
        sort_order: "asc",
        status: "active",
        min_price: 100,
        max_price: 1000,
        category: [1, 2],
        seller: 10,
        brand: 20,
        search: "Test",
      };

      const mockProducts = [{ id: 1, name: "Test Product" }] as Product_Summary[];
      mockEntityManager.createQueryBuilder().getMany = jest.fn().mockResolvedValue(mockProducts);

      const result = await repository.find_by_filters(filters);

      expect(result).toEqual(mockProducts);
    });
  });

  describe("find_by_id", () => {
    it("ID로 상품 검색 성공", async () => {
      const mockProduct = { id: 1, name: "Test Product" } as Product_Catalog;
      mockEntityManager.findOne.mockResolvedValue(mockProduct);

      const result = await repository.find_by_id(1);

      expect(result).toEqual(mockProduct);
    });
  });

  describe("update", () => {
    it("상품 업데이트 성공", async () => {
      const product = {
        id: 1,
        name: "Updated Product",
        seller_id: 10,
        brand_id: 20,
      } as Product;

      mockEntityManager.update.mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(product, 1);

      expect(result).toBe(true);
    });
  });

  describe("delete", () => {
    it("상품 삭제 성공", async () => {
      mockEntityManager.delete.mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(1);

      expect(result).toBe(true);
    });
  });
});
