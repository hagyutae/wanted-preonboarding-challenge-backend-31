import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager, UpdateResult } from "typeorm";

import { ProductCatalogDTO, ProductSummaryDTO } from "@product/application/dto";
import { Product } from "@product/domain/entities";
import ProductRepository from "./Product.repository";
import { BrowsingRepository } from "@browsing/infrastructure/repositories";

describe("ProductRepository", () => {
  let productRepository: ProductRepository;
  let browsingRepository: BrowsingRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductRepository,
        BrowsingRepository,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    productRepository = module.get<ProductRepository>(ProductRepository);
    browsingRepository = module.get<BrowsingRepository>(BrowsingRepository);
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

      const result = await productRepository.save(product);

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

      const mockProducts = [{ id: 1, name: "Test Product" }] as ProductSummaryDTO[];
      mockEntityManager.getRepository().createQueryBuilder().getMany = jest
        .fn()
        .mockResolvedValue(mockProducts);

      const result = await browsingRepository.find_by_filters(filters);

      expect(result).toEqual(mockProducts);
    });
  });

  describe("find_by_id", () => {
    it("ID로 상품 검색 성공", async () => {
      const mockProduct = { id: 1, name: "Test Product" } as ProductCatalogDTO;
      mockEntityManager.findOne.mockResolvedValue(mockProduct);

      const result = await browsingRepository.find_by_id(1);

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

      const result = await productRepository.update(product, 1);

      expect(result).toBe(true);
    });
  });

  describe("delete", () => {
    it("상품 삭제 성공", async () => {
      mockEntityManager.delete.mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await productRepository.delete(1);

      expect(result).toBe(true);
    });
  });
});
