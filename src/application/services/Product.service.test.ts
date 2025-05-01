import { NotFoundException } from "@nestjs/common";
import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import {
  Product,
  Product_Catalog,
  Product_Category,
  Product_Detail,
  Product_Image,
  Product_Option_Group,
  Product_Price,
  Product_Summary,
  Product_Tag,
} from "src/domain/entities";
import { IRepository } from "src/domain/repositories";
import { ProductInputDTO } from "../dto";
import ProductService from "./Product.service";

describe("ProductService", () => {
  let service: ProductService;
  let repository: IRepository<Product | Product_Summary | Product_Catalog>;
  let productDetailRepository: IRepository<Product_Detail>;
  let productPriceRepository: IRepository<Product_Price>;
  let productCategoryRepository: IRepository<Product_Category>;
  let productOptionGroupRepository: IRepository<Product_Option_Group>;
  let productImageRepository: IRepository<Product_Image>;
  let productTagRepository: IRepository<Product_Tag>;
  let entityManager: EntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        {
          provide: "IProductRepository",
          useValue: {
            save: jest.fn(),
            find_by_id: jest.fn(),
            find_by_filters: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
          },
        },
        {
          provide: "IProductDetailRepository",
          useValue: { save: jest.fn(), update: jest.fn() },
        },
        {
          provide: "IProductPriceRepository",
          useValue: { save: jest.fn(), update: jest.fn() },
        },
        {
          provide: "IProductCategoryRepository",
          useValue: { saves: jest.fn(), update: jest.fn() },
        },
        {
          provide: "IProductOptionGroupRepository",
          useValue: { saves: jest.fn() },
        },
        {
          provide: "IProductImageRepository",
          useValue: { saves: jest.fn() },
        },
        {
          provide: "IProductTagRepository",
          useValue: { saves: jest.fn() },
        },
        {
          provide: EntityManager,
          useValue: {
            transaction: jest.fn((callback) => callback({})),
          },
        },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
    repository =
      module.get<IRepository<Product | Product_Summary | Product_Catalog>>("IProductRepository");
    productDetailRepository = module.get<IRepository<Product_Detail>>("IProductDetailRepository");
    productPriceRepository = module.get<IRepository<Product_Price>>("IProductPriceRepository");
    productCategoryRepository = module.get<IRepository<Product_Category>>(
      "IProductCategoryRepository",
    );
    productOptionGroupRepository = module.get<IRepository<Product_Option_Group>>(
      "IProductOptionGroupRepository",
    );
    productImageRepository = module.get<IRepository<Product_Image>>("IProductImageRepository");
    productTagRepository = module.get<IRepository<Product_Tag>>("IProductTagRepository");
    entityManager = module.get<EntityManager>(EntityManager);
  });

  it("상품 등록", async () => {
    const input: ProductInputDTO = {
      name: "슈퍼 편안한 소파",
      slug: "super-comfortable-sofa",
      short_description: "최고급 소재로 만든 편안한 소파",
      full_description: "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
      seller_id: 1,
      brand_id: 2,
      status: "ACTIVE",
      detail: {
        weight: 25.5,
        dimensions: {
          width: 200,
          height: 85,
          depth: 90,
        },
        materials: "가죽, 목재, 폼",
        country_of_origin: "대한민국",
        warranty_info: "2년 품질 보증",
        care_instructions: "마른 천으로 표면을 닦아주세요",
        additional_info: {
          assembly_required: true,
          assembly_time: "30분",
        },
      },
      price: {
        base_price: 599000,
        sale_price: 499000,
        cost_price: 350000,
        currency: "KRW",
        tax_rate: 10,
      },
      categories: [
        {
          category_id: 5,
          is_primary: true,
        },
      ],
      option_groups: [
        {
          name: "색상",
          display_order: 1,
          options: [
            {
              name: "브라운",
              additional_price: 0,
              sku: "SOFA-BRN",
              stock: 10,
              display_order: 1,
            },
          ],
        },
      ],
      images: [
        {
          url: "https://example.com/images/sofa1.jpg",
          alt_text: "브라운 소파 정면",
          is_primary: true,
          display_order: 1,
          option_id: null,
        },
      ],
      tags: [1, 4, 7],
    };
    const savedProduct = {
      id: 1,
      name: "상품명",
      slug: "product-slug",
      created_at: new Date(),
      updated_at: new Date(),
    };
    entityManager.transaction = jest.fn().mockResolvedValue(savedProduct);

    const result = await service.register(input);

    expect(result).toEqual({
      id: 1,
      name: "상품명",
      slug: "product-slug",
      created_at: savedProduct.created_at,
      updated_at: savedProduct.updated_at,
    });
    expect(entityManager.transaction).toHaveBeenCalled();
  });

  it("상품 조회", async () => {
    const product = { id: 1, name: "상품명" };
    repository.find_by_id = jest.fn().mockResolvedValue(product);

    const result = await service.find(1);

    expect(result).toEqual(product);
    expect(repository.find_by_id).toHaveBeenCalledWith(1);
  });

  it("상품 조회 실패 시 NotFoundException 발생", async () => {
    repository.find_by_id = jest.fn().mockResolvedValue(null);

    await expect(service.find(1)).rejects.toThrow(NotFoundException);
    expect(repository.find_by_id).toHaveBeenCalledWith(1);
  });

  it("상품 수정", async () => {
    const input: ProductInputDTO = {
      name: "슈퍼 편안한 소파",
      slug: "super-comfortable-sofa",
      short_description: "최고급 소재로 만든 편안한 소파",
      full_description: "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
      seller_id: 1,
      brand_id: 2,
      status: "ACTIVE",
      detail: {
        weight: 25.5,
        dimensions: {
          width: 200,
          height: 85,
          depth: 90,
        },
        materials: "가죽, 목재, 폼",
        country_of_origin: "대한민국",
        warranty_info: "2년 품질 보증",
        care_instructions: "마른 천으로 표면을 닦아주세요",
        additional_info: {
          assembly_required: true,
          assembly_time: "30분",
        },
      },
      price: {
        base_price: 599000,
        sale_price: 499000,
        cost_price: 350000,
        currency: "KRW",
        tax_rate: 10,
      },
      categories: [
        {
          category_id: 5,
          is_primary: true,
        },
      ],
      option_groups: [
        {
          name: "색상",
          display_order: 1,
          options: [
            {
              name: "브라운",
              additional_price: 0,
              sku: "SOFA-BRN",
              stock: 10,
              display_order: 1,
            },
          ],
        },
      ],
      images: [
        {
          url: "https://example.com/images/sofa1.jpg",
          alt_text: "브라운 소파 정면",
          is_primary: true,
          display_order: 1,
          option_id: null,
        },
      ],
      tags: [1, 4, 7],
    };
    const updatedProduct = {
      id: 1,
      name: "수정된 상품명",
      slug: "updated-slug",
      updated_at: new Date(),
    };
    entityManager.transaction = jest.fn().mockResolvedValue(true);
    repository.find_by_id = jest.fn().mockResolvedValue(updatedProduct);

    const result = await service.edit(1, input);

    expect(result).toEqual({
      id: 1,
      name: "수정된 상품명",
      slug: "updated-slug",
      updated_at: updatedProduct.updated_at,
    });
    expect(entityManager.transaction).toHaveBeenCalled();
  });

  it("상품 수정 실패 시 NotFoundException 발생", async () => {
    entityManager.transaction = jest.fn().mockResolvedValue(false);

    await expect(service.edit(1, {} as ProductInputDTO)).rejects.toThrow(NotFoundException);
  });

  it("상품 삭제", async () => {
    repository.delete = jest.fn().mockResolvedValue(true);
    await service.remove(1);

    expect(repository.delete).toHaveBeenCalledWith(1);
  });

  it("상품 삭제 실패 시 NotFoundException 발생", async () => {
    repository.delete = jest.fn().mockResolvedValue(false);

    await expect(service.remove(1)).rejects.toThrow(NotFoundException);
    expect(repository.delete).toHaveBeenCalledWith(1);
  });
});
