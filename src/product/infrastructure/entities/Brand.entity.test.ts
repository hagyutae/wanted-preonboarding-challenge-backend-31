import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import { get_module } from "__test-utils__/test-module";

import BrandEntity from "./Brand.entity";

describe("BrandEntity", () => {
  let data_source: DataSource;
  let repository: Repository<BrandEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<BrandEntity>>(getRepositoryToken(BrandEntity));
  });

  describe("BrandEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(BrandEntity).metadata;

      expect(entityMetadata.tableName).toBe("brands");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(BrandEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("고유 제약 조건 'slug'", () => {
      const entityMetadata = data_source.getRepository(BrandEntity).metadata;

      const uniqueConstraint = entityMetadata.uniques.find((unique) =>
        unique.columns.some((col) => col.propertyName === "slug"),
      );

      expect(uniqueConstraint).toBeDefined();
    });
  });

  describe("BrandEntity의 CRUD 기능", () => {
    it("BrandEntity를 생성", async () => {
      const brand = new BrandEntity();
      brand.name = "Test Brand";
      brand.slug = "test-brand";
      brand.description = "This is a test brand.";
      brand.logo_url = "http://example.com/logo.png";
      brand.website = "http://example.com";

      repository.save = jest.fn().mockResolvedValue(brand);

      const result = await repository.save(brand);

      expect(result).toEqual(brand);
      expect(result.name).toBe("Test Brand");
      expect(result.slug).toBe("test-brand");
      expect(result.description).toBe("This is a test brand.");
      expect(result.logo_url).toBe("http://example.com/logo.png");
      expect(result.website).toBe("http://example.com");
    });

    it("BrandEntity를 삭제", async () => {
      const brand = new BrandEntity();
      brand.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(brand.id);

      expect(result.affected).toBe(1);
    });

    it("BrandEntity를 조회", async () => {
      const brand = new BrandEntity();
      brand.id = 1;
      brand.name = "Test Brand";
      brand.slug = "test-brand";

      repository.findOne = jest.fn().mockResolvedValue(brand);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(brand);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Test Brand");
      expect(result?.slug).toBe("test-brand");
    });

    it("BrandEntity를 업데이트", async () => {
      const brand = new BrandEntity();
      brand.id = 1;
      brand.name = "Updated Brand";

      repository.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(brand.id, { name: "Updated Brand" });

      expect(result.affected).toBe(1);
    });
  });
});
