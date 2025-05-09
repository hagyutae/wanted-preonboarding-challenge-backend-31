import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository } from "typeorm";

import ProductSummaryView from "./ProductSummary.view";

import { get_module } from "src/__test-utils__/test-module";

describe("ProductSummaryView", () => {
  let data_source: DataSource;
  let repository: Repository<ProductSummaryView>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductSummaryView>>(getRepositoryToken(ProductSummaryView));
  });

  describe("ProductSummaryView 정의", () => {
    it("올바른 뷰 이름", () => {
      const entityMetadata = data_source.getRepository(ProductSummaryView).metadata;

      expect(entityMetadata.tableName).toBe("product_summary_view");
    });

    it("뷰 컬럼 정의", () => {
      const entityMetadata = data_source.getRepository(ProductSummaryView).metadata;

      const columns = [
        "id",
        "name",
        "slug",
        "short_description",
        "base_price",
        "sale_price",
        "currency",
        "primary_image",
        "brand",
        "seller",
        "status",
        "created_at",
        "in_stock",
        "rating",
        "review_count",
      ];

      columns.forEach((column) => {
        const col = entityMetadata.columns.find((c) => c.propertyName === column);
        expect(col).toBeDefined();
      });
    });
  });

  describe("ProductSummaryView 데이터 조회", () => {
    it("뷰 데이터를 조회", async () => {
      const mockData = new ProductSummaryView();
      mockData.id = 1;
      mockData.name = "Test Product";
      mockData.slug = "test-product";
      mockData.short_description = "Test description";
      mockData.base_price = 100;
      mockData.sale_price = 80;
      mockData.currency = "USD";
      mockData.primary_image = { url: "image_url", alt_text: "image_alt" };
      mockData.brand = { id: 1, name: "Test Brand" };
      mockData.seller = { id: 1, name: "Test Seller" };
      mockData.status = "active";
      mockData.created_at = new Date();
      mockData.in_stock = true;
      mockData.rating = 4.5;
      mockData.review_count = 10;

      repository.find = jest.fn().mockResolvedValue([mockData]);

      const result = await repository.find();

      expect(result).toHaveLength(1);
      expect(result[0]).toEqual(mockData);
    });

    it("특정 ID로 뷰 데이터를 조회", async () => {
      const mockData = new ProductSummaryView();
      mockData.id = 1;
      mockData.name = "Test Product";

      repository.findOne = jest.fn().mockResolvedValue(mockData);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(mockData);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Test Product");
    });
  });
});
