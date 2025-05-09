import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import { get_module } from "__test-utils__/test-module";

import ProductEntity from "@product/infrastructure/entities/Product.entity";
import ReviewEntity from "./Review.entity";
import UserEntity from "./User.entity";

describe("ReviewEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ReviewEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ReviewEntity>>(getRepositoryToken(ReviewEntity));
  });

  describe("ReviewEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ReviewEntity).metadata;

      expect(entityMetadata.tableName).toBe("reviews");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ReviewEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("ProductEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ReviewEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "product")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(ProductEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });

    it("UserEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ReviewEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "user")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(UserEntity);
      expect(relation.inverseEntityMetadata.target).toBe(UserEntity);
      expect(relation.isNullable).toBe(true);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ReviewEntity의 CRUD 기능", () => {
    it("ReviewEntity를 생성", async () => {
      const product = new ProductEntity();
      product.id = 1;

      const user = new UserEntity();
      user.id = 1;

      const review = new ReviewEntity();
      review.product = product;
      review.user = user;
      review.rating = 5;
      review.title = "Great product!";
      review.content = "I really enjoyed using this product.";
      review.verified_purchase = true;

      repository.save = jest.fn().mockResolvedValue(review);

      const result = await repository.save(review);

      expect(result).toEqual(review);
      expect(result.product).toEqual(product);
      expect(result.user).toEqual(user);
      expect(result.rating).toBe(5);
      expect(result.title).toBe("Great product!");
      expect(result.content).toBe("I really enjoyed using this product.");
      expect(result.verified_purchase).toBe(true);
    });

    it("ReviewEntity를 삭제", async () => {
      const review = new ReviewEntity();
      review.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(review.id);

      expect(result.affected).toBe(1);
    });

    it("ReviewEntity를 조회", async () => {
      const review = new ReviewEntity();
      review.id = 1;
      review.rating = 5;
      review.title = "Great product!";
      review.content = "I really enjoyed using this product.";

      repository.findOne = jest.fn().mockResolvedValue(review);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(review);
      expect(result?.id).toBe(1);
      expect(result?.rating).toBe(5);
      expect(result?.title).toBe("Great product!");
      expect(result?.content).toBe("I really enjoyed using this product.");
    });

    it("ReviewEntity의 helpful_votes를 업데이트", async () => {
      const review = new ReviewEntity();
      review.id = 1;
      review.helpful_votes = 10;

      repository.update = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.update(review.id, { helpful_votes: 15 });

      expect(result.affected).toBe(1);
    });
  });
});
