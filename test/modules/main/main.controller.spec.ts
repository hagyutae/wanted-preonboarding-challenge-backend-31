import { Test, TestingModule } from '@nestjs/testing';
import { MainController } from '~/modules/main/main.controller';
import { MainService } from '~/modules/main/main.service';

describe('MainController', () => {
  let controller: MainController;
  let service: MainService;

  const mockMainService = {
    getMainPage: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [MainController],
      providers: [
        {
          provide: MainService,
          useValue: mockMainService,
        },
      ],
    }).compile();

    controller = module.get<MainController>(MainController);
    service = module.get<MainService>(MainService);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });

  describe('getMainPage', () => {
    it('메인 페이지 데이터를 조회하고 성공 응답을 반환해야 함', async () => {
      const mockMainPageData = {
        newProducts: [
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
        ],
        popularProducts: [],
        featuredCategories: [
          {
            id: 1,
            name: '테스트 카테고리',
            slug: 'test-category',
            imageUrl: 'category.jpg',
            productCount: 5,
          },
        ],
      };

      mockMainService.getMainPage.mockResolvedValue(mockMainPageData);

      const result = await controller.getMainPage();

      expect(result).toEqual({
        success: true,
        message: '메인 페이지 상품 목록을 성공적으로 조회했습니다.',
        data: mockMainPageData,
      });
      expect(service.getMainPage).toHaveBeenCalled();
    });
  });
});
