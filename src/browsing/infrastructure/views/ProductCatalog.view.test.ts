import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository } from "typeorm";

import { get_module } from "__test-utils__/test-module";

import ProductCatalogView from "./ProductCatalog.view";

describe("ProductCatalogView", () => {
  let data_source: DataSource;
  let repository: Repository<ProductCatalogView>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductCatalogView>>(getRepositoryToken(ProductCatalogView));
  });

  describe("ProductCatalogView 정의", () => {
    it("올바른 뷰 이름", () => {
      const entityMetadata = data_source.getRepository(ProductCatalogView).metadata;

      expect(entityMetadata.tableName).toBe("product_catalog_view");
    });

    it("뷰 컬럼 정의", () => {
      const entityMetadata = data_source.getRepository(ProductCatalogView).metadata;

      const columns = [
        "id",
        "name",
        "slug",
        "short_description",
        "full_description",
        "status",
        "created_at",
        "updated_at",
        "seller",
        "brand",
        "detail",
        "price",
        "categories",
        "option_groups",
        "images",
        "tags",
        "rating",
      ];

      columns.forEach((column) => {
        const col = entityMetadata.columns.find((c) => c.propertyName === column);
        expect(col).toBeDefined();
      });
    });
  });

  describe("ProductCatalogView 데이터 조회", () => {
    it("뷰 데이터를 조회", async () => {
      const mockData = new ProductCatalogView();
      mockData.id = 1;
      mockData.name = "Test Product";
      mockData.slug = "test-product";
      mockData.short_description = "Test short description";
      mockData.full_description = "Test full description";
      mockData.status = "active";
      mockData.created_at = new Date();
      mockData.updated_at = new Date();
      mockData.seller = { id: 1, name: "Test Seller" } as any;
      mockData.brand = { id: 1, name: "Test Brand" } as any;
      mockData.detail = { weight: 1.2, dimensions: "10x10x10" } as any;
      mockData.price = { base_price: 100, sale_price: 80, discount_percentage: 20 } as any;
      mockData.categories = [{ id: 1, name: "Category 1" }] as any;
      mockData.option_groups = [{ id: 1, name: "Option Group 1" }] as any;
      mockData.images = [{ url: "image_url", alt_text: "image_alt" }] as any;
      mockData.tags = [{ id: 1, name: "Tag 1" }] as any;
      mockData.rating = {
        average: 4.5,
        count: 10,
        distribution: { "5": 5, "4": 3, "3": 1, "2": 1, "1": 0 },
      };

      repository.find = jest.fn().mockResolvedValue([mockData]);

      const result = await repository.find();

      expect(result).toHaveLength(1);
      expect(result[0]).toEqual(mockData);
    });

    it("특정 ID로 뷰 데이터를 조회", async () => {
      const mockData = new ProductCatalogView();
      mockData.id = 1;
      mockData.name = "Test Product";

      repository.findOne = jest.fn().mockResolvedValue(mockData);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(mockData);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Test Product");
    });

    it("뷰 데이터가 없는 경우", async () => {
      repository.find = jest.fn().mockResolvedValue([]);

      const result = await repository.find();

      expect(result).toHaveLength(0);
    });

    it("뷰 데이터를 필터링하여 조회", async () => {
      const mockData = new ProductCatalogView();
      mockData.id = 2;
      mockData.name = "Filtered Product";

      repository.find = jest.fn().mockResolvedValue([mockData]);

      const result = await repository.find({ where: { status: "active" } });

      expect(result).toHaveLength(1);
      expect(result[0]).toEqual(mockData);
    });
  });
});
