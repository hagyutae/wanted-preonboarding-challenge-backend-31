import { Test, TestingModule } from "@nestjs/testing";

import IRepository from "src/infrastructure/IRepository";
import ProductService from "./ProductService";

describe("ProductService", () => {
  let service: ProductService;
  let mockRepository: jest.Mocked<IRepository<any>>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductService,
        {
          provide: "IRepository",
          useValue: {
            create: jest.fn(),
            findAll: jest.fn(),
            findById: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<ProductService>(ProductService);
    mockRepository = module.get("IRepository");
  });

  it("create 메서드는 데이터를 생성하고 반환", async () => {
    const data = { name: "Test Product" };
    const createdProduct = { id: "1", ...data };
    mockRepository.create.mockResolvedValue(createdProduct);

    const result = await service.create(data);

    expect(mockRepository.create).toHaveBeenCalledWith(data);
    expect(result).toEqual(createdProduct);
  });

  it("getAll 메서드는 모든 데이터를 조회", async () => {
    await service.getAll();

    expect(mockRepository.findAll).toHaveBeenCalled();
  });

  it("getById 메서드는 ID로 데이터를 조회", async () => {
    const id = "1";
    const product = { id, name: "Test Product" };
    mockRepository.findById.mockResolvedValue(product);

    const result = await service.getById(id);

    expect(mockRepository.findById).toHaveBeenCalledWith(id);
    expect(result).toEqual(product);
  });

  it("update 메서드는 데이터를 수정하고 반환", async () => {
    const id = "1";
    const data = { name: "Updated Product" };
    const updatedProduct = { id, ...data };
    mockRepository.update.mockResolvedValue(updatedProduct);

    const result = await service.update(id, data);

    expect(mockRepository.update).toHaveBeenCalledWith(id, data);
    expect(result).toEqual(updatedProduct);
  });

  it("delete 메서드는 데이터를 삭제", async () => {
    const id = "1";

    await service.delete(id);

    expect(mockRepository.delete).toHaveBeenCalledWith(id);
  });

  it("addOptions 메서드는 옵션을 추가하고 반환", async () => {
    const id = "1";
    const option = { id: "opt1", name: "Option 1" };
    const product = { id, option_groups: [] };
    const updatedProduct = { ...product, option_groups: [option] };
    mockRepository.findById.mockResolvedValue(product);
    mockRepository.update.mockResolvedValue(updatedProduct);

    const result = await service.addOptions(id, option);

    expect(mockRepository.findById).toHaveBeenCalledWith(id);
    expect(mockRepository.update).toHaveBeenCalledWith(id, updatedProduct);
    expect(result).toEqual(updatedProduct);
  });

  it("updateOptions 메서드는 옵션을 수정하고 반환", async () => {
    const id = "1";
    const optionId = "opt1";
    const option = { id: optionId, name: "Updated Option" };
    const product = { id, option_groups: [{ id: optionId, name: "Option 1" }] };
    const updatedProduct = { ...product, option_groups: [option] };
    mockRepository.findById.mockResolvedValue(product);
    mockRepository.update.mockResolvedValue(updatedProduct);

    const result = await service.updateOptions(id, optionId, option);

    expect(mockRepository.findById).toHaveBeenCalledWith(id);
    expect(mockRepository.update).toHaveBeenCalledWith(id, updatedProduct);
    expect(result).toEqual(updatedProduct);
  });

  it("deleteOptions 메서드는 옵션을 삭제하고 반환", async () => {
    const id = "1";
    const optionId = "opt1";
    const product = { id, option_groups: [{ id: optionId, name: "Option 1" }] };
    const updatedProduct = { ...product };
    mockRepository.findById.mockResolvedValue(product);
    mockRepository.update.mockResolvedValue(updatedProduct);

    const result = await service.deleteOptions(id, optionId);

    expect(mockRepository.findById).toHaveBeenCalledWith(id);
    expect(mockRepository.update).toHaveBeenCalledWith(id, updatedProduct);
    expect(result).toEqual(updatedProduct);
  });

  it("addImages 메서드는 이미지를 추가하고 반환", async () => {
    const id = "1";
    const image = { url: "image.jpg" };
    const product = { id, images: [] };
    const updatedProduct = { ...product, images: [image] };
    mockRepository.findById.mockResolvedValue(product);
    mockRepository.update.mockResolvedValue(updatedProduct);

    const result = await service.addImages(id, image);

    expect(mockRepository.findById).toHaveBeenCalledWith(id);
    expect(mockRepository.update).toHaveBeenCalledWith(id, updatedProduct);
    expect(result).toEqual(updatedProduct);
  });
});
