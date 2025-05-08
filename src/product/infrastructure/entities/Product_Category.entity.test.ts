import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import CategoryEntity from "./Category.entity";
import ProductEntity from "./Product.entity";
import ProductCategoryEntity from "./Product_Category.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("ProductCategoryEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductCategoryEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductCategoryEntity>>(
      getRepositoryToken(ProductCategoryEntity),
    );
  });

  describe("ProductCategoryEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductCategoryEntity).metadata;

      expect(entityMetadata.tableName).toBe("product_categories");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductCategoryEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("CategoryEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductCategoryEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "category")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(CategoryEntity);
      expect(relation.inverseEntityMetadata.target).toBe(CategoryEntity);
      expect(relation.isNullable).toBe(true);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductCategoryEntity의 CRUD 기능", () => {
    it("ProductCategoryEntity를 생성", async () => {
      const product = new ProductEntity();
      product.id = 1;

      const category = new CategoryEntity();
      category.id = 1;

      const productCategory = new ProductCategoryEntity();
      productCategory.product = product;
      productCategory.category = category;
      productCategory.is_primary = true;

      repository.save = jest.fn().mockResolvedValue(productCategory);

      const result = await repository.save(productCategory);

      expect(result).toEqual(productCategory);
      expect(result.product).toEqual(product);
      expect(result.category).toEqual(category);
      expect(result.is_primary).toBe(true);
    });

    it("ProductCategoryEntity를 삭제", async () => {
      const productCategory = new ProductCategoryEntity();
      productCategory.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(productCategory.id);

      expect(result.affected).toBe(1);
    });

    it("ProductCategoryEntity를 조회", async () => {
      const productCategory = new ProductCategoryEntity();
      productCategory.id = 1;
      productCategory.is_primary = true;

      repository.findOne = jest.fn().mockResolvedValue(productCategory);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(productCategory);
      expect(result?.id).toBe(1);
      expect(result?.is_primary).toBe(true);
    });
  });
});
