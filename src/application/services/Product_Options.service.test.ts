import { Test, TestingModule } from "@nestjs/testing";
import { NotFoundException } from "@nestjs/common";

import { Product_Image, Product_Option } from "src/domain/entities";
import ProductOptionsService from "./Product_Options.service";
import { IRepository } from "src/domain/repositories";

describe("ProductOptionsService", () => {
  let service: ProductOptionsService;
  let repository: IRepository<Product_Option>;
  let productImageRepository: IRepository<Product_Image>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductOptionsService,
        {
          provide: "IProductOptionsRepository",
          useValue: {
            save: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
            find_by_id: jest.fn(),
          },
        },
        {
          provide: "IProductImageRepository",
          useValue: {
            save: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<ProductOptionsService>(ProductOptionsService);
    repository = module.get<IRepository<Product_Option>>("IProductOptionsRepository");
    productImageRepository = module.get<IRepository<Product_Image>>("IProductImageRepository");
  });

  it("옵션 등록", async () => {
    const id = 1;
    const option_group_id = 2;
    const option = { name: "옵션1" } as Omit<Product_Option, "option_group_id">;
    const savedOption = { id: 1, option_group_id, ...option } as Product_Option;

    repository.save = jest.fn().mockResolvedValue(savedOption);

    const result = await service.register(id, option_group_id, option);

    expect(result).toEqual(savedOption);
    expect(repository.save).toHaveBeenCalledWith({ option_group_id, ...option });
  });

  it("옵션 수정", async () => {
    const product_id = 1;
    const option_id = 1;
    const options = { name: "옵션 수정" } as Omit<Product_Option, "option_group_id">;
    const updatedOption = { id: option_id, ...options } as Product_Option;

    repository.update = jest.fn().mockResolvedValue(true);
    repository.find_by_id = jest.fn().mockResolvedValue(updatedOption);

    const result = await service.update(product_id, option_id, options);

    expect(result).toEqual(updatedOption);
    expect(repository.update).toHaveBeenCalledWith(options, option_id);
    expect(repository.find_by_id).toHaveBeenCalledWith(option_id);
  });

  it("옵션 수정 실패 시 NotFoundException 발생", async () => {
    const product_id = 1;
    const option_id = 1;
    const options = { name: "옵션 수정" } as Omit<Product_Option, "option_group_id">;

    repository.update = jest.fn().mockResolvedValue(false);

    await expect(service.update(product_id, option_id, options)).rejects.toThrow(NotFoundException);
    expect(repository.update).toHaveBeenCalledWith(options, option_id);
  });

  it("옵션 삭제", async () => {
    const product_id = 1;
    const option_id = 1;

    repository.delete = jest.fn().mockResolvedValue(true);

    await service.remove(product_id, option_id);

    expect(repository.delete).toHaveBeenCalledWith(option_id);
  });

  it("옵션 삭제 실패 시 NotFoundException 발생", async () => {
    const product_id = 1;
    const option_id = 1;

    repository.delete = jest.fn().mockResolvedValue(false);

    await expect(service.remove(product_id, option_id)).rejects.toThrow(NotFoundException);
    expect(repository.delete).toHaveBeenCalledWith(option_id);
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

    productImageRepository.save = jest.fn().mockResolvedValue(savedImage);

    const result = await service.register_images(id, option_id, image);

    expect(result).toEqual({ id: 1, url: "image-url", product_id: 1, option_id: undefined });
    expect(productImageRepository.save).toHaveBeenCalledWith({
      product_id: id,
      option_id,
      ...image,
    });
  });
});
