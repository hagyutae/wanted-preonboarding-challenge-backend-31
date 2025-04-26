import IRepository from "src/infrastructure/IRepository";
import Product_Service from "./Product_Service";

describe("Product_Service", () => {
  let service: Product_Service;
  let repositoryMock: jest.Mocked<IRepository<any>>;

  beforeEach(() => {
    repositoryMock = {
      create: jest.fn(),
      findAll: jest.fn(),
      findById: jest.fn(),
      update: jest.fn(),
      delete: jest.fn(),
    };

    service = new Product_Service(repositoryMock);
  });

  it("create 호출 시 repository.create를 호출", async () => {
    const data = { name: "Test Product" };

    await service.create(data);

    expect(repositoryMock.create).toHaveBeenCalledWith(data);
  });

  it("getAll 호출 시 repository.findAll을 호출", async () => {
    await service.getAll();

    expect(repositoryMock.findAll).toHaveBeenCalled();
  });

  it("getById 호출 시 repository.findById를 호출", async () => {
    const id = "123";

    await service.getById(id);

    expect(repositoryMock.findById).toHaveBeenCalledWith(id);
  });

  it("update 호출 시 repository.update를 호출", async () => {
    const id = "123";
    const data = { name: "Updated Product" };

    await service.update(id, data);

    expect(repositoryMock.update).toHaveBeenCalledWith(id, data);
  });

  it("delete 호출 시 repository.delete를 호출", async () => {
    const id = "123";

    await service.delete(id);

    expect(repositoryMock.delete).toHaveBeenCalledWith(id);
  });
});
