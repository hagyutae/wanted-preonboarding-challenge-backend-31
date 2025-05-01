import { NotFoundException } from "@nestjs/common";
import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { ProductInputDTO } from "../dto";
import ProductService from "./Product.service";

describe("ProductService", () => {
  let service: ProductService;
  const mockProductRepository = global.mockProductRepository;
  const mockEntityManager = global.mockEntityManager;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        ...global.mockRepositoryProviders,
        {
          provide: EntityManager,
          useValue: mockEntityManager,
        },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
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
    mockEntityManager.transaction = jest.fn().mockResolvedValue(savedProduct);

    const result = await service.register(input);

    expect(result).toEqual({
      id: 1,
      name: "상품명",
      slug: "product-slug",
      created_at: savedProduct.created_at,
      updated_at: savedProduct.updated_at,
    });
    expect(mockEntityManager.transaction).toHaveBeenCalled();
  });

  it("상품 조회", async () => {
    const product = { id: 1, name: "상품명" };
    mockProductRepository.find_by_id = jest.fn().mockResolvedValue(product);

    const result = await service.find(1);

    expect(result).toEqual(product);
    expect(mockProductRepository.find_by_id).toHaveBeenCalledWith(1);
  });

  it("상품 조회 실패 시 NotFoundException 발생", async () => {
    mockProductRepository.find_by_id = jest.fn().mockResolvedValue(null);

    await expect(service.find(1)).rejects.toThrow(NotFoundException);
    expect(mockProductRepository.find_by_id).toHaveBeenCalledWith(1);
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
    mockEntityManager.transaction = jest.fn().mockResolvedValue(true);
    mockProductRepository.find_by_id = jest.fn().mockResolvedValue(updatedProduct);

    const result = await service.edit(1, input);

    expect(result).toEqual({
      id: 1,
      name: "수정된 상품명",
      slug: "updated-slug",
      updated_at: updatedProduct.updated_at,
    });
    expect(mockEntityManager.transaction).toHaveBeenCalled();
  });

  it("상품 수정 실패 시 NotFoundException 발생", async () => {
    mockEntityManager.transaction = jest.fn().mockResolvedValue(false);

    await expect(service.edit(1, {} as ProductInputDTO)).rejects.toThrow(NotFoundException);
  });

  it("상품 삭제", async () => {
    mockProductRepository.delete = jest.fn().mockResolvedValue(true);
    await service.remove(1);

    expect(mockProductRepository.delete).toHaveBeenCalledWith(1);
  });

  it("상품 삭제 실패 시 NotFoundException 발생", async () => {
    mockProductRepository.delete = jest.fn().mockResolvedValue(false);

    await expect(service.remove(1)).rejects.toThrow(NotFoundException);
    expect(mockProductRepository.delete).toHaveBeenCalledWith(1);
  });
});
