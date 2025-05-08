import { Test, TestingModule } from '@nestjs/testing';
import { CategoriesService } from '~/modules/categories/categories.service';
import { CategoryRepository } from '~/modules/categories/categories.repository';
import { NotFoundException } from '@nestjs/common';
import { GetProductsByCategoryIdRequestDto } from '~/modules/categories/dto/category.dto';

describe('CategoriesService', () => {
  let service: CategoriesService;
  let repository: CategoryRepository;

  const mockCategoryRepository = {
    getCategories: jest.fn(),
    getCategoryById: jest.fn(),
    getProductsByCategoryId: jest.fn(),
    getProductsCountByCategoryId: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        CategoriesService,
        {
          provide: CategoryRepository,
          useValue: mockCategoryRepository,
        },
      ],
    }).compile();

    service = module.get<CategoriesService>(CategoriesService);
    repository = module.get<CategoryRepository>(CategoryRepository);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('getCategories', () => {
    it('카테고리 목록을 조회하고 성공 응답을 반환해야 함', async () => {
      const level = 1;
      const mockCategories = [
        {
          id: 1,
          name: '테스트 카테고리',
          description: '테스트 카테고리 설명',
        },
      ];

      mockCategoryRepository.getCategories.mockResolvedValue(mockCategories);

      const result = await service.getCategories(level);

      expect(result).toEqual(mockCategories);
      expect(repository.getCategories).toHaveBeenCalledWith(level);
    });
  });

  describe('getCategoryById', () => {
    it('카테고리 상세 정보를 조회하고 성공 응답을 반환해야 함', async () => {
      const categoryId = 1;

      const mockCategory = {
        id: categoryId,
        name: '테스트 카테고리',
        description: '테스트 카테고리 설명',
      };

      mockCategoryRepository.getCategoryById.mockResolvedValue(mockCategory);

      const result = await service.getCategoryById(categoryId);

      expect(result).toEqual(mockCategory);
      expect(repository.getCategoryById).toHaveBeenCalledWith(categoryId);
    });

    it('존재하지 않는 카테고리를 조회하면 NotFoundException을 던져야 함', async () => {
      const categoryId = 999;

      mockCategoryRepository.getCategoryById.mockResolvedValue(null);

      await expect(service.getCategoryById(categoryId)).rejects.toThrow(
        new NotFoundException('카테고리를 찾을 수 없습니다.'),
      );
      expect(repository.getCategoryById).toHaveBeenCalledWith(categoryId);
    });
  });

  describe('getProductsByCategoryId', () => {
    it('카테고리별 상품 목록을 조회하고 성공 응답을 반환해야 함', async () => {
      const categoryId = 1;
      const query: GetProductsByCategoryIdRequestDto = {
        page: 1,
        per_page: 10,
        sort: 'created_at:desc',
        includeSubCategories: true,
      };

      const mockProducts = [
        {
          id: 1,
          name: '테스트 상품',
        },
      ];

      const mockTotal = 1;

      mockCategoryRepository.getProductsByCategoryId.mockResolvedValue(
        mockProducts,
      );
      mockCategoryRepository.getProductsCountByCategoryId.mockResolvedValue(
        mockTotal,
      );

      const result = await service.getProductsByCategoryId(categoryId, query);

      expect(result).toEqual({
        items: mockProducts,
        total: mockTotal,
      });
      expect(repository.getProductsByCategoryId).toHaveBeenCalledWith(
        categoryId,
        query,
      );
      expect(repository.getProductsCountByCategoryId).toHaveBeenCalledWith(
        categoryId,
        query,
      );
    });
  });
});
