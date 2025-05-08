import { Test, TestingModule } from '@nestjs/testing';
import { MainService } from '~/modules/main/main.service';
import { MainRepository } from '~/modules/main/main.repository';

describe('MainService', () => {
  let service: MainService;
  let repository: MainRepository;

  const mockMainRepository = {
    getNewProducts: jest.fn(),
    getPopularProducts: jest.fn(),
    getFeaturedCategories: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        MainService,
        {
          provide: MainRepository,
          useValue: mockMainRepository,
        },
      ],
    }).compile();

    service = module.get<MainService>(MainService);
    repository = module.get<MainRepository>(MainRepository);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('getMainPage', () => {
    it('메인 페이지 데이터를 조회하고 성공 응답을 반환해야 함', async () => {
      const mockNewProducts = [
        {
          id: 1,
          name: '테스트 상품',
          slug: 'test-product',
          shortDescription: '테스트 상품 설명',
          status: 'ACTIVE',
          createdAt: new Date(),
          basePrice: 10000,
          salePrice: null,
          currency: 'KRW',
          primaryImage: {
            url: 'test.jpg',
            altText: '테스트 이미지',
          },
          brand: {
            id: 1,
            name: '테스트 브랜드',
            logoUrl: 'brand.jpg',
          },
          seller: {
            id: 1,
            name: '테스트 판매자',
            logoUrl: 'seller.jpg',
          },
          rating: 4.5,
          reviewCount: 10,
          inStock: true,
        },
      ];

      const mockPopularProducts = [];
      const mockFeaturedCategories = [
        {
          id: 1,
          name: '테스트 카테고리',
          slug: 'test-category',
          imageUrl: 'category.jpg',
          productCount: 5,
        },
      ];

      mockMainRepository.getNewProducts.mockResolvedValue(mockNewProducts);
      mockMainRepository.getPopularProducts.mockResolvedValue(
        mockPopularProducts,
      );
      mockMainRepository.getFeaturedCategories.mockResolvedValue(
        mockFeaturedCategories,
      );

      const result = await service.getMainPage();

      expect(result).toEqual({
        newProducts: mockNewProducts,
        popularProducts: mockPopularProducts,
        featuredCategories: mockFeaturedCategories,
      });
      expect(repository.getNewProducts).toHaveBeenCalled();
      expect(repository.getPopularProducts).toHaveBeenCalled();
      expect(repository.getFeaturedCategories).toHaveBeenCalled();
    });
  });
});
