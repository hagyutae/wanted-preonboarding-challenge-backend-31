import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import ProductEntity from "./Product.entity";
import ProductDetailEntity from "./Product_Detail.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("ProductDetailEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductDetailEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductDetailEntity>>(
      getRepositoryToken(ProductDetailEntity),
    );
  });

  describe("ProductDetailEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductDetailEntity).metadata;

      expect(entityMetadata.tableName).toBe("product_details");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductDetailEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("ProductEntity와 일대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductDetailEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "product")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("one-to-one");
      expect(relation.type).toBe(ProductEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductDetailEntity의 CRUD 기능", () => {
    it("ProductDetailEntity를 생성", async () => {
      const product = new ProductEntity();
      product.id = 1;

      const productDetail = new ProductDetailEntity();
      productDetail.product = product;
      productDetail.weight = 1.5;
      productDetail.dimensions = { depth: 10, width: 5, height: 2 };
      productDetail.materials = "Steel";
      productDetail.country_of_origin = "USA";
      productDetail.warranty_info = "2 years";
      productDetail.care_instructions = "Wipe with a dry cloth";
      productDetail.additional_info = { assembly_required: false, assembly_time: "30분" };

      repository.save = jest.fn().mockResolvedValue(productDetail);

      const result = await repository.save(productDetail);

      expect(result).toEqual(productDetail);
      expect(result.product).toEqual(product);
      expect(result.weight).toBe(1.5);
      expect(result.dimensions).toEqual({ length: 10, width: 5, height: 2 });
      expect(result.materials).toBe("Steel");
      expect(result.country_of_origin).toBe("USA");
      expect(result.warranty_info).toBe("2 years");
      expect(result.care_instructions).toBe("Wipe with a dry cloth");
      expect(result.additional_info).toEqual({ color: "red" });
    });

    it("ProductDetailEntity를 삭제", async () => {
      const productDetail = new ProductDetailEntity();
      productDetail.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(productDetail.id);

      expect(result.affected).toBe(1);
    });

    it("ProductDetailEntity를 조회", async () => {
      const productDetail = new ProductDetailEntity();
      productDetail.id = 1;
      productDetail.weight = 1.5;

      repository.findOne = jest.fn().mockResolvedValue(productDetail);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(productDetail);
      expect(result?.id).toBe(1);
      expect(result?.weight).toBe(1.5);
    });
  });
});
