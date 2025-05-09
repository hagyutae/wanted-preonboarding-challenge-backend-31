import { Test, TestingModule } from "@nestjs/testing";

import { IBrowsingRepository } from "@libs/domain/repositories";
import BrowsingService from "./Browsing.service";

describe("BrowsingService", () => {
  let service: BrowsingService;
  let repository: jest.Mocked<IBrowsingRepository>;

  beforeEach(async () => {
    const mockRepository: jest.Mocked<IBrowsingRepository> = {
      find_by_filters: jest.fn(),
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

    repository.get_featured_categories.mockResolvedValue(featuredCategories);

    const result = await service.find();

    expect(result).toEqual({
      new_products: newProducts,
      popular_products: popularProducts,
      featured_categories: featuredCategories,
    });
    expect(repository.get_featured_categories).toHaveBeenCalled();
  });
});
