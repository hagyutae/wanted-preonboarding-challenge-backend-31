import { Test, TestingModule } from "@nestjs/testing";
import { NotFoundException } from "@nestjs/common";

import { Product_Image, Product_Option } from "src/domain/entities";
import ProductOptionsService from "./Product_Options.service";

describe("ProductOptionsService", () => {
  let service: ProductOptionsService;
  const mockProductOptionsRepository = global.mockProductOptionsRepository;
  const mockProductImageRepository = global.mockProductImageRepository;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ProductOptionsService, ...global.mockRepositoryProviders],
    }).compile();

    service = module.get<ProductOptionsService>(ProductOptionsService);
  });

  it("옵션 등록", async () => {
    const id = 1;
    const option_group_id = 2;
    const option = { name: "옵션1" } as Omit<Product_Option, "option_group_id">;
    const savedOption = { id: 1, option_group_id, ...option } as Product_Option;

    mockProductOptionsRepository.save = jest.fn().mockResolvedValue(savedOption);

    const result = await service.register(id, option_group_id, option);

    expect(result).toEqual(savedOption);
    expect(mockProductOptionsRepository.save).toHaveBeenCalledWith({ option_group_id, ...option });
  });

  it("옵션 수정", async () => {
    const product_id = 1;
    const option_id = 1;
    const options = { name: "옵션 수정" } as Omit<Product_Option, "option_group_id">;
    const updatedOption = { id: option_id, ...options } as Product_Option;

    mockProductOptionsRepository.update = jest.fn().mockResolvedValue(true);
    mockProductOptionsRepository.find_by_id = jest.fn().mockResolvedValue(updatedOption);

    const result = await service.update(product_id, option_id, options);

    expect(result).toEqual(updatedOption);
    expect(mockProductOptionsRepository.update).toHaveBeenCalledWith(options, option_id);
    expect(mockProductOptionsRepository.find_by_id).toHaveBeenCalledWith(option_id);
  });

  it("옵션 수정 실패 시 NotFoundException 발생", async () => {
    const product_id = 1;
    const option_id = 1;
    const options = { name: "옵션 수정" } as Omit<Product_Option, "option_group_id">;

    mockProductOptionsRepository.update = jest.fn().mockResolvedValue(false);

    await expect(service.update(product_id, option_id, options)).rejects.toThrow(NotFoundException);
    expect(mockProductOptionsRepository.update).toHaveBeenCalledWith(options, option_id);
  });

  it("옵션 삭제", async () => {
    const product_id = 1;
    const option_id = 1;

    mockProductOptionsRepository.delete = jest.fn().mockResolvedValue(true);

    await service.remove(product_id, option_id);

    expect(mockProductOptionsRepository.delete).toHaveBeenCalledWith(option_id);
  });

  it("옵션 삭제 실패 시 NotFoundException 발생", async () => {
    const product_id = 1;
    const option_id = 1;

    mockProductOptionsRepository.delete = jest.fn().mockResolvedValue(false);

    await expect(service.remove(product_id, option_id)).rejects.toThrow(NotFoundException);
    expect(mockProductOptionsRepository.delete).toHaveBeenCalledWith(option_id);
  });

  it("옵션 이미지 등록", async () => {
    const id = 1;
    const option_id = 1;
    const image = { url: "image-url" } as Omit<Product_Image, "product_id" | "option_id">;
    const savedImage = {
      id: 1,
      product_id: id,
      option_id,
      ...image,
    } as Product_Image;

    mockProductImageRepository.save = jest.fn().mockResolvedValue(savedImage);

    const result = await service.register_images(id, option_id, image);

    expect(result).toEqual({ id: 1, url: "image-url", product_id: 1, option_id: undefined });
    expect(mockProductImageRepository.save).toHaveBeenCalledWith({
      product_id: id,
      option_id,
      ...image,
    });
  });
});
