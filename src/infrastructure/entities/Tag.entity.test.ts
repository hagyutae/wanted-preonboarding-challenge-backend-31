import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import TagEntity from "./Tag.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("TagEntity", () => {
  let data_source: DataSource;
  let repository: Repository<TagEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<TagEntity>>(getRepositoryToken(TagEntity));
  });

  describe("TagEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(TagEntity).metadata;

      expect(entityMetadata.tableName).toBe("tags");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(TagEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("고유 제약 조건 'slug'", () => {
      const entityMetadata = data_source.getRepository(TagEntity).metadata;

      const uniqueConstraint = entityMetadata.uniques.find((unique) =>
        unique.columns.some((col) => col.propertyName === "slug"),
      );

      expect(uniqueConstraint).toBeDefined();
    });
  });

  describe("TagEntity의 CRUD 기능", () => {
    it("TagEntity를 생성", async () => {
      const tag = new TagEntity();
      tag.name = "Test Tag";
      tag.slug = "test-tag";

      repository.save = jest.fn().mockResolvedValue(tag);

      const result = await repository.save(tag);

      expect(result).toEqual(tag);
      expect(result.name).toBe("Test Tag");
      expect(result.slug).toBe("test-tag");
    });

    it("TagEntity를 삭제", async () => {
      const tag = new TagEntity();
      tag.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(tag.id);

      expect(result.affected).toBe(1);
    });

    it("TagEntity를 조회", async () => {
      const tag = new TagEntity();
      tag.id = 1;
      tag.name = "Test Tag";
      tag.slug = "test-tag";

      repository.findOne = jest.fn().mockResolvedValue(tag);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(tag);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Test Tag");
      expect(result?.slug).toBe("test-tag");
    });

    it("TagEntity를 업데이트", async () => {
      const tag = new TagEntity();
      tag.id = 1;
      tag.name = "Updated Tag";
      tag.slug = "updated-tag";

      repository.save = jest.fn().mockResolvedValue(tag);

      const result = await repository.save(tag);

      expect(result).toEqual(tag);
      expect(result.name).toBe("Updated Tag");
      expect(result.slug).toBe("updated-tag");
    });
  });
});
