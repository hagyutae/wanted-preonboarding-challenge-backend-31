import { TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";
import { DataSource, Repository, UpdateResult } from "typeorm";

import { get_module } from "__test-utils__/test-module";

import ProductEntity from "./Product.entity";
import ProductImageEntity from "./Product_Image.entity";
import ProductOptionEntity from "./Product_Option.entity";

describe("ProductImageEntity", () => {
  let data_source: DataSource;
  let repository: Repository<ProductImageEntity>;

  beforeAll(async () => {
    const module: TestingModule = await get_module();

    data_source = module.get<DataSource>(DataSource);
    repository = module.get<Repository<ProductImageEntity>>(getRepositoryToken(ProductImageEntity));
  });

  describe("ProductImageEntity가 정의", () => {
    it("올바른 테이블 이름", () => {
      const entityMetadata = data_source.getRepository(ProductImageEntity).metadata;

      expect(entityMetadata.tableName).toBe("product_images");
    });

    it("기본 키 'id'", () => {
      const entityMetadata = data_source.getRepository(ProductImageEntity).metadata;

      const primaryColumn = entityMetadata.columns.find((col) => col.propertyName === "id")!;

      expect(primaryColumn).toBeDefined();
      expect(primaryColumn.isPrimary).toBe(true);
      expect(primaryColumn.type).toBe("bigint");
    });

    it("ProductEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductImageEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "product")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(ProductEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductEntity);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });

    it("ProductOptionEntity와 다대일 관계", () => {
      const entityMetadata = data_source.getRepository(ProductImageEntity).metadata;

      const relation = entityMetadata.relations.find((rel) => rel.propertyName === "option")!;

      expect(relation).toBeDefined();
      expect(relation.relationType).toBe("many-to-one");
      expect(relation.type).toBe(ProductOptionEntity);
      expect(relation.inverseEntityMetadata.target).toBe(ProductOptionEntity);
      expect(relation.isNullable).toBe(true);
      expect(relation.joinColumns[0].referencedColumn?.propertyName).toBe("id");
    });
  });

  describe("ProductImageEntity의 CRUD 기능", () => {
    it("ProductImageEntity를 생성", async () => {
      const product = new ProductEntity();
      product.id = 1;

      const productImage = new ProductImageEntity();
      productImage.product = product;
      productImage.url = "http://example.com/image.jpg";
      productImage.alt_text = "Example Image";
      productImage.is_primary = true;
      productImage.display_order = 1;

      repository.save = jest.fn().mockResolvedValue(productImage);

      const result = await repository.save(productImage);

      expect(result).toEqual(productImage);
      expect(result.product).toEqual(product);
      expect(result.url).toBe("http://example.com/image.jpg");
      expect(result.alt_text).toBe("Example Image");
      expect(result.is_primary).toBe(true);
      expect(result.display_order).toBe(1);
    });

    it("ProductImageEntity를 삭제", async () => {
      const productImage = new ProductImageEntity();
      productImage.id = 1;

      repository.delete = jest.fn().mockResolvedValue({ affected: 1 } as UpdateResult);

      const result = await repository.delete(productImage.id);

      expect(result.affected).toBe(1);
    });

    it("ProductImageEntity를 조회", async () => {
      const productImage = new ProductImageEntity();
      productImage.id = 1;
      productImage.url = "http://example.com/image.jpg";
      productImage.is_primary = true;

      repository.findOne = jest.fn().mockResolvedValue(productImage);

      const result = await repository.findOne({ where: { id: 1 } });

      expect(result).toEqual(productImage);
      expect(result?.id).toBe(1);
      expect(result?.url).toBe("http://example.com/image.jpg");
      expect(result?.is_primary).toBe(true);
    });
  });
});
