import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import ProductEntity from "./Product.entity";
import SellerEntity from "./Seller.entity";
import BrandEntity from "./Brand.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("ProductEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductEntity>>(getRepositoryToken(ProductEntity));
  });

  describe("ProductEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductEntity).metadata;

      expect(entityMetadata.tableName).toBe("products");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("SellerEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "seller")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(SellerEntity);
      expect(relation.inverseEntityMetadata.target).toBe(SellerEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });

    it("BrandEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "brand")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(BrandEntity);
      expect(relation.inverseEntityMetadata.target).toBe(BrandEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductEntity의 CRUD 기능", () => {
    it("ProductEntity를 생성", async () => {
      const seller = new SellerEntity();
      seller.id = 1;

      const brand = new BrandEntity();
      brand.id = 1;

      const product = new ProductEntity();
      product.name = "Test Product";
      product.slug = "test-product";
      product.short_description = "Short description";
      product.full_description = "Full description";
      product.seller = seller;
      product.brand = brand;
      product.status = "active";

      repository.save = jest.fn().mockResolvedValue(product);

      const result = await repository.save(product);

      expect(result).toEqual(product);
      expect(result.name).toBe("Test Product");
      expect(result.slug).toBe("test-product");
      expect(result.seller).toEqual(seller);
      expect(result.brand).toEqual(brand);
      expect(result.status).toBe("active");
    });

    it("ProductEntity를 삭제", async () => {
      const product = new ProductEntity();
      product.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(product.id);

      expect(result.affected).toBe(1);
    });

    it("ProductEntity를 조회", async () => {
      const product = new ProductEntity();
      product.id = 1;
      product.name = "Test Product";

      repository.findOne = jest.fn().mockResolvedValue(product);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(product);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Test Product");
    });

    it("ProductEntity를 업데이트", async () => {
      const product = new ProductEntity();
      product.id = 1;
      product.name = "Updated Product";

      repository.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(product.id, { name: "Updated Product" });

      expect(result.affected).toBe(1);
    });
  });
});
