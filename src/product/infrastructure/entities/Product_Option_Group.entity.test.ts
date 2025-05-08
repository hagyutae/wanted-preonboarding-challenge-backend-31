import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import ProductEntity from "./Product.entity";
import ProductOptionGroupEntity from "./Product_Option_Group.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("ProductOptionGroupEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductOptionGroupEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductOptionGroupEntity>>(
      getRepositoryToken(ProductOptionGroupEntity),
    );
  });

  describe("ProductOptionGroupEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductOptionGroupEntity).metadata;

      expect(entityMetadata.tableName).toBe("product_option_groups");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductOptionGroupEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("ProductEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductOptionGroupEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "product")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(ProductEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductOptionGroupEntity의 CRUD 기능", () => {
    it("ProductOptionGroupEntity를 생성", async () => {
      const product = new ProductEntity();
      product.id = 1;

      const productOptionGroup = new ProductOptionGroupEntity();
      productOptionGroup.product = product;
      productOptionGroup.name = "Option Group 1";
      productOptionGroup.display_order = 1;

      repository.save = jest.fn().mockResolvedValue(productOptionGroup);

      const result = await repository.save(productOptionGroup);

      expect(result).toEqual(productOptionGroup);
      expect(result.product).toEqual(product);
      expect(result.name).toBe("Option Group 1");
      expect(result.display_order).toBe(1);
    });

    it("ProductOptionGroupEntity를 삭제", async () => {
      const productOptionGroup = new ProductOptionGroupEntity();
      productOptionGroup.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(productOptionGroup.id);

      expect(result.affected).toBe(1);
    });

    it("ProductOptionGroupEntity를 조회", async () => {
      const productOptionGroup = new ProductOptionGroupEntity();
      productOptionGroup.id = 1;
      productOptionGroup.name = "Option Group 1";
      productOptionGroup.display_order = 1;

      repository.findOne = jest.fn().mockResolvedValue(productOptionGroup);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(productOptionGroup);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Option Group 1");
      expect(result?.display_order).toBe(1);
    });
  });
});
