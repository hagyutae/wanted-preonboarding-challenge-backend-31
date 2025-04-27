import { Test, TestingModule } from "@nestjs/testing";

import ProductService from "src/application/ProductService";
import { GetQueryDTO, PostBodyDTO } from "../dto";
import ProductController from "./Product.controller";

describe("ProductController", () => {
  let controller: ProductController;
  let mockService: jest.Mocked<ProductService>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ProductController],
      providers: [
        {
          provide: ProductService,
          useValue: {
            create: jest.fn(),
            getById: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
          },
        },
      ],
    }).compile();

    controller = module.get<ProductController>(ProductController);
    mockService = module.get(ProductService);
  });

  it("create 메서드는 상품을 생성하고 성공 메시지를 반환", async () => {
    const body = { name: "Test Product" } as PostBodyDTO;
    const createdProduct = { id: "1", ...body };
    mockService.create.mockResolvedValue(createdProduct);

    const result = await controller.create(body);

    expect(mockService.create).toHaveBeenCalledWith(body);
    expect(result).toEqual({
      success: true,
      data: createdProduct,
      message: "상품이 성공적으로 등록되었습니다.",
    });
  });

  it("read 메서드는 ID로 상품을 조회하고 성공 메시지를 반환", async () => {
    const id = "1";
    const query: GetQueryDTO = {};
    const product = { id, name: "Test Product" };
    mockService.getById.mockResolvedValue(product);

    const result = await controller.read({ id }, query);

    expect(mockService.getById).toHaveBeenCalledWith(id);
    expect(result).toEqual({
      success: true,
      data: product,
      message: "상품 상세 정보를 성공적으로 조회했습니다.",
    });
  });

  it("update 메서드는 상품을 수정하고 성공 메시지를 반환", async () => {
    const id = "1";
    const body = { name: "Updated Product" } as PostBodyDTO;
    const updatedProduct = { id, ...body };
    mockService.update.mockResolvedValue(updatedProduct);

    const result = await controller.update({ id }, body);

    expect(mockService.update).toHaveBeenCalledWith(id, body);
    expect(result).toEqual({
      success: true,
      data: updatedProduct,
      message: "상품이 성공적으로 수정되었습니다.",
    });
  });

  it("delete 메서드는 상품을 삭제하고 성공 메시지를 반환", async () => {
    const id = "1";
    mockService.delete.mockResolvedValue(undefined);

    const result = await controller.delete({ id });

    expect(mockService.delete).toHaveBeenCalledWith(id);
    expect(result).toEqual({
      success: true,
      data: undefined,
      message: "상품이 성공적으로 삭제되었습니다.",
    });
  });
});
