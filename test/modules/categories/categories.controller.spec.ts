import { Test, TestingModule } from '@nestjs/testing';
import { CategoriesController } from '~/modules/categories/categories.controller';
import { CategoriesService } from '~/modules/categories/categories.service';
import { GetCategoriesRequestDto } from '~/modules/categories/dto/category.dto';

describe('CategoriesController', () => {
  let controller: CategoriesController;
  let categoriesService: CategoriesService;

  const mockCategoriesService = {
    getCategories: jest.fn(),
    getCategoryById: jest.fn(),
    getProductsByCategoryId: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [CategoriesController],
      providers: [
        {
          provide: CategoriesService,
          useValue: mockCategoriesService,
        },
      ],
    }).compile();

    controller = module.get<CategoriesController>(CategoriesController);
    categoriesService = module.get<CategoriesService>(CategoriesService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  describe('getCategories', () => {
    it('카테고리 목록을 조회하고 성공 응답을 반환해야 함', async () => {
      const query: GetCategoriesRequestDto = {
        level: 1,
      };

      const mockCategories = [
        {
          id: 1,
          name: '테스트 카테고리',
          description: '테스트 카테고리 설명',
        },
      ];

      mockCategoriesService.getCategories.mockResolvedValue(mockCategories);

      const result = await controller.getCategories(query);

      expect(result).toEqual({
        success: true,
        message: '카테고리 목록을 성공적으로 조회했습니다.',
        data: mockCategories,
      });
      expect(categoriesService.getCategories).toHaveBeenCalledWith(query.level);
    });
  });

  describe('getProductsByCategoryId', () => {
    it('카테고리별 상품 목록을 조회하고 성공 응답을 반환해야 함', async () => {
      const categoryId = 1;
      const query = {
        page: 1,
        per_page: 10,
        sort: 'created_at:desc' as const,
        includeSubCategories: true,
      };

      const mockCategory = {
        id: categoryId,
        name: '테스트 카테고리',
      };

      const mockProducts = {
        items: [
          {
            id: 1,
            name: '테스트 상품',
          },
        ],
        total: 1,
      };

      mockCategoriesService.getCategoryById.mockResolvedValue(mockCategory);
      mockCategoriesService.getProductsByCategoryId.mockResolvedValue(
        mockProducts,
      );

      const result = await controller.getProductsByCategoryId(
        categoryId,
        query,
      );

      expect(result).toEqual({
        success: true,
        message: '카테고리 상품 목록을 성공적으로 조회했습니다.',
        data: {
          category: mockCategory,
          items: mockProducts.items,
          pagination: {
            current_page: query.page,
            per_page: query.per_page,
            total_items: mockProducts.total,
            total_pages: Math.ceil(mockProducts.total / query.per_page),
          },
        },
      });
      expect(categoriesService.getCategoryById).toHaveBeenCalledWith(
        categoryId,
      );
      expect(categoriesService.getProductsByCategoryId).toHaveBeenCalledWith(
        categoryId,
        query,
      );
    });
  });
});
