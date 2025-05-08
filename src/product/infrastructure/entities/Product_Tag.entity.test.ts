import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import { get_module } from "__test-utils__/test-module";

import ProductEntity from "./Product.entity";
import ProductTagEntity from "./Product_Tag.entity";
import TagEntity from "./Tag.entity";

describe("ProductTagEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductTagEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductTagEntity>>(getRepositoryToken(ProductTagEntity));
  });

  describe("ProductTagEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductTagEntity).metadata;

      expect(entityMetadata.tableName).toBe("product_tags");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductTagEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("ProductEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductTagEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "product")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(ProductEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });

    it("TagEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductTagEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "tag")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(TagEntity);
      expect(relation.inverseEntityMetadata.target).toBe(TagEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductTagEntity의 CRUD 기능", () => {
    it("ProductTagEntity를 생성", async () => {
      const product = new ProductEntity();
      product.id = 1;

      const tag = new TagEntity();
      tag.id = 1;

      const productTag = new ProductTagEntity();
      productTag.product = product;
      productTag.tag = tag;

      repository.save = jest.fn().mockResolvedValue(productTag);

      const result = await repository.save(productTag);

      expect(result).toEqual(productTag);
      expect(result.product).toEqual(product);
      expect(result.tag).toEqual(tag);
    });

    it("ProductTagEntity를 삭제", async () => {
      const productTag = new ProductTagEntity();
      productTag.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(productTag.id);

      expect(result.affected).toBe(1);
    });

    it("ProductTagEntity를 조회", async () => {
      const productTag = new ProductTagEntity();
      productTag.id = 1;

      repository.findOne = jest.fn().mockResolvedValue(productTag);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(productTag);
      expect(result?.id).toBe(1);
    });
  });
});
