import { Test, TestingModule } from "@nestjs/testing";

import IRepository from "src/infrastructure/IRepository";
import ProductService from "./ProductService";

describe("Product_Service", () => {
  let service: ProductService;
  let repositoryMock: jest.Mocked<IRepository<any>>;

  beforeEach(async () => {
    repositoryMock = {
      create: jest.fn(),
      findAll: jest.fn(),
      findById: jest.fn(),
      update: jest.fn(),
      delete: jest.fn(),
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        { provide: "IRepository", useValue: repositoryMock },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
  });

  it("create 메서드는 데이터를 생성하고 성공 응답을 반환", async () => {
    const data = { name: "Test Product" };
    const createdProduct = { id: "1", ...data };
    repositoryMock.create.mockResolvedValue(createdProduct);

    const response = await service.create(data);

    expect(repositoryMock.create).toHaveBeenCalledWith(data);
    expect(response).toEqual({
      success: true,
      data: createdProduct,
      message: "상품이 성공적으로 등록되었습니다.",
    });
  });

  it("getAll 메서드는 모든 데이터를 조회", async () => {
    await service.getAll();

    expect(repositoryMock.findAll).toHaveBeenCalled();
  });

  it("getById 메서드는 ID로 데이터를 조회하고 성공 응답을 반환", async () => {
    const id = "1";
    const product = { id, name: "Test Product" };
    repositoryMock.findById.mockResolvedValue(product);

    const response = await service.getById(id);

    expect(repositoryMock.findById).toHaveBeenCalledWith(id);
    expect(response).toEqual({
      success: true,
      data: product,
      message: "상품 상세 정보를 성공적으로 조회했습니다.",
    });
  });

  it("update 메서드는 데이터를 수정하고 성공 응답을 반환", async () => {
    const id = "1";
    const data = { name: "Updated Product" };
    const updatedProduct = { id, ...data };
    repositoryMock.update.mockResolvedValue(updatedProduct);

    const response = await service.update(id, data);

    expect(repositoryMock.update).toHaveBeenCalledWith(id, data);
    expect(response).toEqual({
      success: true,
      data: updatedProduct,
      message: "상품이 성공적으로 수정되었습니다.",
    });
  });

  it("delete 메서드는 데이터를 삭제하고 성공 응답을 반환", async () => {
    const id = "1";
    const response = await service.delete(id);

    expect(repositoryMock.delete).toHaveBeenCalledWith(id);
    expect(response).toEqual({
      success: true,
      data: null,
      message: "상품이 성공적으로 삭제되었습니다.",
    });
  });

  it("addOption 메서드는 옵션을 추가하고 성공 응답을 반환", async () => {
    const id = "1";
    const option = { id: "opt1", name: "Option 1" };
    const product = { id, option_groups: [] };
    const updatedProduct = { ...product, option_groups: [option] };
    repositoryMock.findById.mockResolvedValue(product);
    repositoryMock.update.mockResolvedValue(updatedProduct);

    const response = await service.addOptions(id, option);

    expect(repositoryMock.findById).toHaveBeenCalledWith(id);
    expect(repositoryMock.update).toHaveBeenCalledWith(id, updatedProduct);
    expect(response).toEqual({
      success: true,
      data: updatedProduct,
      message: "상품 옵션이 성공적으로 추가되었습니다.",
    });
  });

  it("updateOption 메서드는 옵션을 수정하고 성공 응답을 반환", async () => {
    const id = "1";
    const option = { id: "opt1", name: "Updated Option" };
    const product = { id, option_groups: [{ id: "opt1", name: "Option 1" }] };
    const updatedProduct = { ...product, option_groups: [option] };
    repositoryMock.findById.mockResolvedValue(product);
    repositoryMock.update.mockResolvedValue(updatedProduct);

    const response = await service.updateOptions(id, option.id, option);

    expect(repositoryMock.findById).toHaveBeenCalledWith(id);
    expect(repositoryMock.update).toHaveBeenCalledWith(id, updatedProduct);
    expect(response).toEqual({
      success: true,
      data: updatedProduct,
      message: "상품 옵션이 성공적으로 수정되었습니다.",
    });
  });

  it("deleteOption 메서드는 옵션을 삭제하고 성공 응답을 반환", async () => {
    const id = "1";
    const optionId = "opt1";
    const product = { id, option_groups: [{ id: optionId, name: "Option 1" }] };
    const updatedProduct = { ...product, option_groups: [] };
    repositoryMock.findById.mockResolvedValue(product);
    repositoryMock.update.mockResolvedValue(updatedProduct);

    const response = await service.deleteOptions(id, optionId);

    expect(repositoryMock.findById).toHaveBeenCalledWith(optionId);
    expect(repositoryMock.update).toHaveBeenCalledWith(
      optionId,
      updatedProduct,
    );
    expect(response).toEqual({
      success: true,
      data: null,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    });
  });
});
