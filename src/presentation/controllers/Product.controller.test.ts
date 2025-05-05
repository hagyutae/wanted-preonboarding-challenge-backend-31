import { Test, TestingModule } from "@nestjs/testing";

import { ProductService } from "src/application/services";
import { ProductEntity } from "src/infrastructure/entities";
import { ProductCatalogView, ProductSummaryView } from "src/infrastructure/views";
import { ProductBodyDTO, ParamDTO, ProductQueryDTO, ResponseDTO } from "../dto";
import ProductController from "./Product.controller";

describe("ProductController", () => {
  let productController: ProductController;
  let productService: ProductService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ProductController],
      providers: [
        {
          provide: ProductService,
          useValue: {
            create: jest.fn(),
            getAll: jest.fn(),
            getById: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
          },
        },
      ],
    }).compile();

    productController = module.get<ProductController>(ProductController);
    productService = module.get<ProductService>(ProductService);
  });

  it("상품 생성", async () => {
    const body = { name: "상품1" } as ProductBodyDTO;
    const response: ResponseDTO<any> = {
      success: true,
      data: { id: 1, ...body },
      message: "상품이 성공적으로 등록되었습니다.",
    };
    productService.register = jest.fn().mockResolvedValue(response.data as ProductEntity);

    const result = await productController.create(body);

    expect(result).toEqual(response);
    expect(productService.register).toHaveBeenCalledWith(body);
  });

  it("모든 상품을 조회", async () => {
    const query = { page: 1, perPage: 10 } as ProductQueryDTO;
    const items = [
      {
        id: 1,
        name: "상품1",
        slug: "product-1",
        created_at: new Date(),
        status: "available",
      },
    ] as ProductSummaryView[];
    const pagination = { total_items: 1, total_pages: 1, current_page: 1, per_page: 10 };
    const response: ResponseDTO<any> = {
      success: true,
      data: { items, pagination },
      message: "상품 목록을 성공적으로 조회했습니다.",
    };
    productService.find_all = jest.fn().mockResolvedValue({ items, pagination });

    const result = await productController.read_all(query);

    expect(result).toEqual(response);
    expect(productService.find_all).toHaveBeenCalledWith(query);
  });

  it("id로 상품을 조회", async () => {
    const param: ParamDTO = { id: 1 };
    const data = {
      id: 1,
      name: "상품1",
      slug: "product-1",
      created_at: new Date(),
      updated_at: new Date(),
      status: "available",
    } as ProductCatalogView;
    const response: ResponseDTO<any> = {
      success: true,
      data,
      message: "상품 상세 정보를 성공적으로 조회했습니다.",
    };
    productService.find = jest.fn().mockResolvedValue(data);

    const result = await productController.read(param);

    expect(result).toEqual(response);
    expect(productService.find).toHaveBeenCalledWith(param.id);
  });

  it("상품을 수정", async () => {
    const param: ParamDTO = { id: 1 };
    const body = { name: "상품1 수정" } as ProductBodyDTO;
    const data = {
      id: 1,
      name: "상품1",
      slug: "product-1",
      updated_at: new Date(),
    };
    const response: ResponseDTO<any> = {
      success: true,
      data,
      message: "상품이 성공적으로 수정되었습니다.",
    };
    productService.edit = jest.fn().mockResolvedValue(data);

    const result = await productController.update(param, body);

    expect(result).toEqual(response);
    expect(productService.edit).toHaveBeenCalledWith(param.id, body);
  });

  it("상품을 삭제", async () => {
    const param = { id: 1 } as ParamDTO;
    const response: ResponseDTO<any> = {
      success: true,
      data: null,
      message: "상품이 성공적으로 삭제되었습니다.",
    };
    productService.remove = jest.fn().mockResolvedValue(undefined);

    const result = await productController.delete(param);

    expect(result).toEqual(response);
    expect(productService.remove).toHaveBeenCalledWith(param.id);
  });
});
