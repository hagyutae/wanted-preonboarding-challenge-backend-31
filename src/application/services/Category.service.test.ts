import { Test, TestingModule } from "@nestjs/testing";

import { Category, Product, Product_Catalog, Product_Summary } from "src/domain/entities";
import { IRepository } from "src/domain/repositories";
import { FilterDTO } from "../dto";
import CategoryService from "./Category.service";

describe("CategoryService", () => {
  let categoryService: CategoryService;
  let categoryRepository: IRepository<Category>;
  let productRepository: IRepository<Product | Product_Summary | Product_Catalog>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CategoryService,
        {
          provide: "ICategoryRepository",
          useValue: {
            find_by_filters: jest.fn(),
            find_by_id: jest.fn(),
          },
        },
        {
          provide: "IProductRepository",
          useValue: {
            find_by_filters: jest.fn(),
          },
        },
      ],
    }).compile();

    categoryService = module.get<CategoryService>(CategoryService);
    categoryRepository = module.get<IRepository<Category>>("ICategoryRepository");
    productRepository =
      module.get<IRepository<Product | Product_Summary | Product_Catalog>>("IProductRepository");
  });

  describe("find_all_as_tree", () => {
    it("카테고리를 트리 구조로 반환", async () => {
      const categories = [
        { id: 1, name: "대분류1", parent: null },
        { id: 2, name: "중분류1", parent: { id: 1 } as Category },
        { id: 3, name: "소분류1", parent: { id: 2 } as Category },
      ] as Category[];
      categoryRepository.find_by_filters = jest.fn().mockResolvedValue(categories);

      const result = await categoryService.find_all_as_tree();

      expect(result).toEqual([
        {
          id: 1,
          name: "대분류1",
          children: [
            {
              id: 2,
              name: "중분류1",
              children: [
                {
                  id: 3,
                  name: "소분류1",
                },
              ],
            },
          ],
        },
      ]);
      expect(categoryRepository.find_by_filters).toHaveBeenCalledWith({});
    });

    it("레벨 제한을 초과한 경우 빈 배열 반환", async () => {
      const categories: Category[] = [];
      categoryRepository.find_by_filters = jest.fn().mockResolvedValue(categories);

      const result = await categoryService.find_all_as_tree(4);

      expect(result).toEqual([]);
      expect(categoryRepository.find_by_filters).toHaveBeenCalledWith({});
    });
  });

  describe("find_products_by_category_id", () => {
    const category = { id: 1, name: "대분류1", parent: { id: 0 } } as Category;
    const items = [
      { id: 1, name: "상품1", created_at: new Date() },
      { id: 2, name: "상품2", created_at: new Date() },
    ] as Product_Summary[];

    beforeEach(() => {
      categoryRepository.find_by_id = jest.fn().mockResolvedValue(category);
      productRepository.find_by_filters = jest.fn().mockResolvedValue(items);
    });

    it("카테고리 ID로 상품 조회", async () => {
      const filter = { page: 1, per_page: 10, sort: "created_at:desc", has_sub: true } as FilterDTO;

      const result = await categoryService.find_products_by_category_id(1, filter);

      expect(result).toEqual({
        category,
        items,
        pagination: {
          total_items: items.length,
          total_pages: Math.ceil(items.length / (filter.per_page ?? 10)),
          current_page: filter.page ?? 1,
          per_page: filter.per_page ?? 10,
        },
      });
    });

    it("하위 카테고리 제외 시 부모 정보 제거", async () => {
      const filter = {
        page: 1,
        per_page: 10,
        sort: "created_at:desc",
        has_sub: false,
      } as FilterDTO;

      const result = await categoryService.find_products_by_category_id(1, filter);

      expect(result.category).toEqual({ id: 1, name: "대분류1" });
    });
  });
});
