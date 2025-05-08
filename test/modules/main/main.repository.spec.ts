import { Test, TestingModule } from '@nestjs/testing';
import { MainRepository } from '~/modules/main/main.repository';
import { DrizzleService } from '~/database/drizzle.service';

describe('MainRepository', () => {
  let repository: MainRepository;
  let drizzleService: DrizzleService;

  const mockDrizzleService = {
    db: {
      query: {
        products: {
          findMany: jest.fn(),
        },
        categories: {
          findMany: jest.fn(),
        },
      },
      select: jest.fn().mockReturnValue({
        from: jest.fn().mockReturnValue({
          where: jest.fn().mockReturnValue([{ count: 10 }]),
        }),
      }),
    },
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        MainRepository,
        {
          provide: DrizzleService,
          useValue: mockDrizzleService,
        },
      ],
    }).compile();

    repository = module.get<MainRepository>(MainRepository);
    drizzleService = module.get<DrizzleService>(DrizzleService);
  });

  it('should be defined', () => {
    expect(repository).toBeDefined();
  });

  describe('getNewProducts', () => {
    it('최신 상품 목록을 조회해야 함', async () => {
      const mockProducts = [
        {
          id: 1,
          name: '테스트 상품',
          slug: 'test-product',
          shortDescription: '테스트 상품 설명',
          status: 'ACTIVE',
          createdAt: new Date(),
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
          images: [
            {
              url: 'test.jpg',
              altText: '테스트 이미지',
            },
          ],
          price: {
            basePrice: 10000,
            salePrice: null,
            currency: 'KRW',
          },
          reviews: [
            {
              rating: 5,
            },
          ],
          optionGroups: [
            {
              options: [
                {
                  stock: 10,
                },
              ],
            },
          ],
        },
      ];

      mockDrizzleService.db.query.products.findMany.mockResolvedValue(
        mockProducts,
      );

      const result = await repository.getNewProducts();

      expect(result).toHaveLength(1);
      expect(result[0]).toHaveProperty('id', 1);
      expect(result[0]).toHaveProperty('name', '테스트 상품');
      expect(result[0]).toHaveProperty('rating', 5);
      expect(result[0]).toHaveProperty('reviewCount', 1);
      expect(result[0]).toHaveProperty('inStock', true);
      expect(mockDrizzleService.db.query.products.findMany).toHaveBeenCalled();
    });
  });

  describe('getPopularProducts', () => {
    it('인기 상품 목록을 조회해야 함', async () => {
      const mockProducts = [
        {
          id: 1,
          name: '테스트 상품',
          slug: 'test-product',
          shortDescription: '테스트 상품 설명',
          status: 'ACTIVE',
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
          images: [
            {
              url: 'test.jpg',
              altText: '테스트 이미지',
            },
          ],
          price: {
            basePrice: 10000,
            salePrice: null,
            currency: 'KRW',
          },
          reviews: [
            {
              rating: 5,
            },
          ],
          optionGroups: [
            {
              options: [
                {
                  stock: 10,
                },
              ],
            },
          ],
        },
      ];

      mockDrizzleService.db.query.products.findMany.mockResolvedValue(
        mockProducts,
      );

      const result = await repository.getPopularProducts();

      expect(result).toHaveLength(1);
      expect(result[0]).toHaveProperty('id', 1);
      expect(result[0]).toHaveProperty('name', '테스트 상품');
      expect(result[0]).toHaveProperty('rating', 5);
      expect(result[0]).toHaveProperty('reviewCount', 1);
      expect(result[0]).toHaveProperty('inStock', true);
      expect(mockDrizzleService.db.query.products.findMany).toHaveBeenCalled();
    });
  });

  describe('getFeaturedCategories', () => {
    it('추천 카테고리 목록을 조회해야 함', async () => {
      const mockCategories = [
        {
          id: 1,
          name: '테스트 카테고리',
          slug: 'test-category',
          imageUrl: 'category.jpg',
        },
      ];

      mockDrizzleService.db.query.categories.findMany.mockResolvedValue(
        mockCategories,
      );
      mockDrizzleService.db.select.mockReturnValue({
        from: jest.fn().mockReturnValue({
          where: jest.fn().mockReturnValue([{ count: 5 }]),
        }),
      });

      const result = await repository.getFeaturedCategories();

      expect(result).toHaveLength(1);
      expect(result[0]).toHaveProperty('id', 1);
      expect(result[0]).toHaveProperty('name', '테스트 카테고리');
      expect(result[0]).toHaveProperty('productCount', 5);
      expect(
        mockDrizzleService.db.query.categories.findMany,
      ).toHaveBeenCalled();
    });
  });
});
