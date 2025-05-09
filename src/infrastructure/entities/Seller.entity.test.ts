import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import SellerEntity from "./Seller.entity";

import { get_module } from "src/__test-utils__/test-module";

describe("SellerEntity", () => {
  let data_source: DataSource;
  let repository: Repository<SellerEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<SellerEntity>>(getRepositoryToken(SellerEntity));
  });

  describe("SellerEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(SellerEntity).metadata;

      expect(entityMetadata.tableName).toBe("sellers");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(SellerEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });
  });

  describe("SellerEntity의 CRUD 기능", () => {
    it("SellerEntity를 생성", async () => {
      const seller = new SellerEntity();
      seller.name = "Test Seller";
      seller.description = "A test seller description";
      seller.logo_url = "http://example.com/logo.png";
      seller.rating = 4.5;
      seller.contact_email = "test@example.com";
      seller.contact_phone = "123-456-7890";

      repository.save = jest.fn().mockResolvedValue(seller);

      const result = await repository.save(seller);

      expect(result).toEqual(seller);
      expect(result.name).toBe("Test Seller");
      expect(result.description).toBe("A test seller description");
      expect(result.logo_url).toBe("http://example.com/logo.png");
      expect(result.rating).toBe(4.5);
      expect(result.contact_email).toBe("test@example.com");
      expect(result.contact_phone).toBe("123-456-7890");
    });

    it("SellerEntity를 삭제", async () => {
      const seller = new SellerEntity();
      seller.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(seller.id);

      expect(result.affected).toBe(1);
    });

    it("SellerEntity를 조회", async () => {
      const seller = new SellerEntity();
      seller.id = 1;
      seller.name = "Test Seller";

      repository.findOne = jest.fn().mockResolvedValue(seller);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(seller);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("Test Seller");
    });
  });
});
