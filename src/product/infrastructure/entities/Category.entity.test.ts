import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import { get_module } from "__test-utils__/test-module";

import CategoryEntity from "./Category.entity";

describe("CategoryEntity", () => {
  let data_source: DataSource;
  let repository: Repository<CategoryEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<CategoryEntity>>(getRepositoryToken(CategoryEntity));
  });

  describe("CategoryEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(CategoryEntity).metadata;

      expect(entityMetadata.tableName).toBe("categories");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(CategoryEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("고유 제약 조건 'slug'", () => {
      const entityMetadata = data_source.getRepository(CategoryEntity).metadata;

      const uniqueConstraint = entityMetadata.uniques.find((unique) =>
        unique.columns.some((col) => col.propertyName === "slug"),
      );

      expect(uniqueConstraint).toBeDefined();
    });

    it("CategoryEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(CategoryEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "parent")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(CategoryEntity);
      expect(relation.inverseEntityMetadata.target).toBe(CategoryEntity);
      expect(relation.isNullable).toBe(true);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("CategoryEntity의 CRUD 기능", () => {
    it("CategoryEntity를 생성", async () => {
      const category = new CategoryEntity();
      category.name = "Test Category";
      category.slug = "test-category";
      category.description = "This is a test category.";
      category.level = 1;
      category.image_url = "http://example.com/image.png";

      repository.save = jest.fn().mockResolvedValue(category);

      const result = await repository.save(category);

      expect(result).toEqual(category);
      expect(result.name).toBe("Test Category");
      expect(result.slug).toBe("test-category");
      expect(result.description).toBe("This is a test category.");
      expect(result.level).toBe(1);
      expect(result.image_url).toBe("http://example.com/image.png");
    });

    it("CategoryEntity를 삭제", async () => {
      const category = new CategoryEntity();
      category.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(category.id);

      expect(result.affected).toBe(1);
    });

    it("CategoryEntity를 조회", async () => {
      const category = new CategoryEntity();
      category.id = 1;
      category.name = "Test Category";
      category.slug = "test-category";

      repository.findOne = jest.fn().mockResolvedValue(category);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(category);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Test Category");
      expect(result?.slug).toBe("test-category");
    });

    it("CategoryEntity를 업데이트", async () => {
      const category = new CategoryEntity();
      category.id = 1;
      category.name = "Updated Category";

      repository.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(category.id, { name: "Updated Category" });

      expect(result.affected).toBe(1);
    });

    it("CategoryEntity의 부모 카테고리 설정", async () => {
      const parentCategory = new CategoryEntity();
      parentCategory.id = 1;
      parentCategory.name = "Parent Category";

      const childCategory = new CategoryEntity();
      childCategory.name = "Child Category";
      childCategory.slug = "child-category";
      childCategory.level = 2;
      childCategory.parent = parentCategory;

      repository.save = jest.fn().mockResolvedValue(childCategory);

      const result = await repository.save(childCategory);

      expect(result).toEqual(childCategory);
      expect(result.parent).toEqual(parentCategory);
      expect(result.level).toBe(2);
    });
  });
});
