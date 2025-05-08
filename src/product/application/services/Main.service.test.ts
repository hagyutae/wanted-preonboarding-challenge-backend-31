import { Test, TestingModule } from "@nestjs/testing";

import { IMainRepository } from "src/libs/domain/repositories";
import MainService from "./Main.service";

describe("MainService", () => {
  let mainService: MainService;
  let repository: jest.Mocked<IMainRepository>;

  beforeEach(async () => {
    const mockRepository: jest.Mocked<IMainRepository> = {
      get_new_products: jest.fn(),
      get_popular_products: jest.fn(),
      get_featured_categories: jest.fn(),
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        MainService,
        {
          provide: "IMainRepository",
          useValue: mockRepository,
        },
      ],
    }).compile();

    mainService = module.get<MainService>(MainService);
    repository = module.get("IMainRepository");
  });

  it("새로운 상품, 인기 상품, 추천 카테고리를 조회", async () => {
    const newProducts = [
      { id: 1, name: "상품1", slug: "product-1" },
      { id: 2, name: "상품2", slug: "product-2" },
    ];
    const popularProducts = [
      { id: 3, name: "상품3", slug: "product-3" },
      { id: 4, name: "상품4", slug: "product-4" },
    ];
    const featuredCategories = [
      { id: 1, name: "카테고리1" },
      { id: 2, name: "카테고리2" },
    ];

    repository.get_new_products.mockResolvedValue(newProducts);
    repository.get_popular_products.mockResolvedValue(popularProducts);
    repository.get_featured_categories.mockResolvedValue(featuredCategories);

    const result = await mainService.find();

    expect(result).toEqual({
      new_products: newProducts,
      popular_products: popularProducts,
      featured_categories: featuredCategories,
    });
    expect(repository.get_new_products).toHaveBeenCalledWith(1, 5);
    expect(repository.get_popular_products).toHaveBeenCalled();
    expect(repository.get_featured_categories).toHaveBeenCalled();
  });
});
