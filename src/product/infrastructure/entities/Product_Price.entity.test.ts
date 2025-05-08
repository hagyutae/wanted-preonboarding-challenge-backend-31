import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import ProductEntity from "./Product.entity";
import ProductPriceEntity from "./Product_Price.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("ProductPriceEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductPriceEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductPriceEntity>>(getRepositoryToken(ProductPriceEntity));
  });

  describe("ProductPriceEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductPriceEntity).metadata;

      expect(entityMetadata.tableName).toBe("product_prices");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductPriceEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("ProductEntity와 일대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductPriceEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "product")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("one-to-one");
      expect(relation.type).toBe(ProductEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductPriceEntity의 CRUD 기능", () => {
    it("ProductPriceEntity를 생성", async () => {
      const product = new ProductEntity();
      product.id = 1;

      const productPrice = new ProductPriceEntity();
      productPrice.product = product;
      productPrice.base_price = 1000.0;
      productPrice.sale_price = 900.0;
      productPrice.cost_price = 800.0;
      productPrice.currency = "KRW";
      productPrice.tax_rate = 10.0;

      repository.save = jest.fn().mockResolvedValue(productPrice);

      const result = await repository.save(productPrice);

      expect(result).toEqual(productPrice);
      expect(result.product).toEqual(product);
      expect(result.base_price).toBe(1000.0);
      expect(result.sale_price).toBe(900.0);
      expect(result.cost_price).toBe(800.0);
      expect(result.currency).toBe("KRW");
      expect(result.tax_rate).toBe(10.0);
    });

    it("ProductPriceEntity를 삭제", async () => {
      const productPrice = new ProductPriceEntity();
      productPrice.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(productPrice.id);

      expect(result.affected).toBe(1);
    });

    it("ProductPriceEntity를 조회", async () => {
      const productPrice = new ProductPriceEntity();
      productPrice.id = 1;
      productPrice.base_price = 1000.0;
      productPrice.currency = "KRW";

      repository.findOne = jest.fn().mockResolvedValue(productPrice);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(productPrice);
      expect(result?.id).toBe(1);
      expect(result?.base_price).toBe(1000.0);
      expect(result?.currency).toBe("KRW");
    });

    it("ProductPriceEntity를 업데이트", async () => {
      const productPrice = new ProductPriceEntity();
      productPrice.id = 1;
      productPrice.sale_price = 850.0;

      repository.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(productPrice.id, { sale_price: 850.0 });

      expect(result.affected).toBe(1);
    });
  });
});
