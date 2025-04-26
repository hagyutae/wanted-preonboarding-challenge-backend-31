import { Repository, DeleteResult, UpdateResult } from "typeorm";
import { Test, TestingModule } from "@nestjs/testing";
import { getRepositoryToken } from "@nestjs/typeorm";

import ProductRepository from "./ProductRepository";
import { Product } from "./entities/Product.entity";

describe("ProductRepository", () => {
  let repository: ProductRepository;
  let mockRepository: jest.Mocked<Repository<Product>>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductRepository,
        {
          provide: getRepositoryToken(Product),
          useValue: {
            create: jest.fn(),
            save: jest.fn(),
            find: jest.fn(),
            findOne: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
          },
        },
      ],
    }).compile();

    repository = module.get<ProductRepository>(ProductRepository);
    mockRepository = module.get(getRepositoryToken(Product));
  });

  it("create 메서드는 데이터를 생성하고 저장", async () => {
    const data = { name: "Test Product" } as Product;
    const { id, ...rest } = data;
    const createdProduct = { id: "1", ...rest } as Product;
    mockRepository.create.mockReturnValue(createdProduct);
    mockRepository.save.mockResolvedValue(createdProduct);

    const result = await repository.create(data);

    expect(mockRepository.create).toHaveBeenCalledWith(data);
    expect(mockRepository.save).toHaveBeenCalledWith(createdProduct);
    expect(result).toEqual(createdProduct);
  });

  it("findAll 메서드는 모든 데이터를 조회", async () => {
    const products = [{ id: "1", name: "Test Product" }] as Product[];
    mockRepository.find.mockResolvedValue(products);

    const result = await repository.findAll();

    expect(mockRepository.find).toHaveBeenCalled();
    expect(result).toEqual(products);
  });

  it("findById 메서드는 ID로 데이터를 조회", async () => {
    const id = "1";
    const product = { id, name: "Test Product" } as Product;
    mockRepository.findOne.mockResolvedValue(product);

    const result = await repository.findById(id);

    expect(mockRepository.findOne).toHaveBeenCalledWith({ where: { id } });
    expect(result).toEqual(product);
  });

  it("update 메서드는 데이터를 수정하고 수정된 데이터를 반환", async () => {
    const id = "1";
    const data = { name: "Updated Product" } as Partial<Product>;
    const updatedProduct = { id, ...data } as Product;
    mockRepository.update.mockResolvedValue({ affected: 1 } as UpdateResult);
    mockRepository.findOne.mockResolvedValue(updatedProduct);

    const result = await repository.update(id, data);

    expect(mockRepository.update).toHaveBeenCalledWith(id, data);
    expect(mockRepository.findOne).toHaveBeenCalledWith({ where: { id } });
    expect(result).toEqual(updatedProduct);
  });

  it("delete 메서드는 데이터를 삭제", async () => {
    const id = "1";
    mockRepository.delete.mockResolvedValue({ affected: 1 } as DeleteResult);

    await repository.delete(id);

    expect(mockRepository.delete).toHaveBeenCalledWith(id);
  });
});
