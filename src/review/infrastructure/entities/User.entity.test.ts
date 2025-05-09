import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import { get_module } from "__test-utils__/test-module";

import UserEntity from "./User.entity";

describe("UserEntity", () => {
  let data_source: DataSource;
  let repository: Repository<UserEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<UserEntity>>(getRepositoryToken(UserEntity));
  });

  describe("UserEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(UserEntity).metadata;

      expect(entityMetadata.tableName).toBe("users");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(UserEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });
  });

  describe("UserEntity의 CRUD 기능", () => {
    it("UserEntity를 생성", async () => {
      const user = new UserEntity();
      user.name = "John Doe";
      user.email = "john.doe@example.com";
      user.avatar_url = "http://example.com/avatar.jpg";

      repository.save = jest.fn().mockResolvedValue(user);

      const result = await repository.save(user);

      expect(result).toEqual(user);
      expect(result.name).toBe("John Doe");
      expect(result.email).toBe("john.doe@example.com");
      expect(result.avatar_url).toBe("http://example.com/avatar.jpg");
    });

    it("UserEntity를 삭제", async () => {
      const user = new UserEntity();
      user.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(user.id);

      expect(result.affected).toBe(1);
    });

    it("UserEntity를 조회", async () => {
      const user = new UserEntity();
      user.id = 1;
      user.name = "John Doe";
      user.email = "john.doe@example.com";

      repository.findOne = jest.fn().mockResolvedValue(user);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(user);
      expect(result?.id).toBe(1);
      expect(result?.name).toBe("John Doe");
      expect(result?.email).toBe("john.doe@example.com");
    });
  });
});
