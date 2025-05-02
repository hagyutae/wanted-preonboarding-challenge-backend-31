import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import ProductOptionEntity from "./Product_Option.entity";
import ProductOptionGroupEntity from "./Product_Option_Group.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("ProductOptionEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductOptionEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductOptionEntity>>(
      getRepositoryToken(ProductOptionEntity),
    );
  });

  describe("ProductOptionEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductOptionEntity).metadata;

      expect(entityMetadata.tableName).toBe("product_options");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductOptionEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("ProductOptionGroupEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductOptionEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "option_group")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(ProductOptionGroupEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductOptionGroupEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductOptionEntity의 CRUD 기능", () => {
    it("ProductOptionEntity를 생성", async () => {
      const optionGroup = new ProductOptionGroupEntity();
      optionGroup.id = 1;

      const productOption = new ProductOptionEntity();
      productOption.option_group = optionGroup;
      productOption.name = "Option 1";
      productOption.additional_price = 10.5;
      productOption.sku = "SKU123";
      productOption.stock = 100;
      productOption.display_order = 1;

      repository.save = jest.fn().mockResolvedValue(productOption);

      const result = await repository.save(productOption);

      expect(result).toEqual(productOption);
      expect(result.option_group).toEqual(optionGroup);
      expect(result.name).toBe("Option 1");
      expect(result.additional_price).toBe(10.5);
      expect(result.sku).toBe("SKU123");
      expect(result.stock).toBe(100);
      expect(result.display_order).toBe(1);
    });

    it("ProductOptionEntity를 삭제", async () => {
      const productOption = new ProductOptionEntity();
      productOption.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(productOption.id);

      expect(result.affected).toBe(1);
    });

    it("ProductOptionEntity를 조회", async () => {
      const productOption = new ProductOptionEntity();
      productOption.id = 1;
      productOption.name = "Option 1";

      repository.findOne = jest.fn().mockResolvedValue(productOption);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(productOption);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Option 1");
    });

    it("ProductOptionEntity를 업데이트", async () => {
      const productOption = new ProductOptionEntity();
      productOption.id = 1;
      productOption.name = "Updated Option";

      repository.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(productOption.id, { name: "Updated Option" });

      expect(result.affected).toBe(1);
    });
  });
});
