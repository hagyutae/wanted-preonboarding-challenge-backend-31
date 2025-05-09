import { Test, TestingModule } from "@nestjs/testing";

import { IBrowsingRepository } from "@shared/repositories";
import BrowsingService from "./Browsing.service";

describe("BrowsingService", () => {
  let service: BrowsingService;
  let repository: jest.Mocked<IBrowsingRepository>;

  beforeEach(async () => {
    const mockRepository: jest.Mocked<IBrowsingRepository> = {
      find_by_filters: jest.fn(),
      find_by_id: jest.fn(),
      get_featured_categories: jest.fn(),
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        BrowsingService,
        {
          provide: "IBrowsingRepository",
          useValue: mockRepository,
        },
      ],
    }).compile();

    service = module.get<BrowsingService>(BrowsingService);
    repository = module.get("IBrowsingRepository");
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

    repository.find_by_filters
      .mockResolvedValueOnce(newProducts) // new_products에 대한 호출
      .mockResolvedValueOnce(popularProducts); // popular_products에 대한 호출
    repository.get_featured_categories.mockResolvedValue(featuredCategories);

    const result = await service.find();

    expect(repository.find_by_filters).toHaveBeenCalledTimes(2);
    expect(repository.find_by_filters).toHaveBeenCalledWith({
      sort_field: "created_at",
      sort_order: "DESC",
    });
    expect(repository.find_by_filters).toHaveBeenCalledWith({
      sort_field: "rating",
      sort_order: "DESC",
    });
    expect(repository.get_featured_categories).toHaveBeenCalledTimes(1);

    expect(result).toEqual({
      new_products: newProducts,
      popular_products: popularProducts,
      featured_categories: featuredCategories,
    });
  });
});
