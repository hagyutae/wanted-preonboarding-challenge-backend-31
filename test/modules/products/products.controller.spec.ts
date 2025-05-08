import { Test, TestingModule } from '@nestjs/testing';
import { ProductsController } from '~/modules/products/products.controller';
import { ProductsService } from '~/modules/products/products.service';
import { ReviewsService } from '~/modules/reviews/reviews.service';

describe('ProductsController', () => {
  let controller: ProductsController;
  let productsService: ProductsService;
  let reviewsService: ReviewsService;

  const mockProductsService = {
    getProducts: jest.fn(),
    getProduct: jest.fn(),
    createProduct: jest.fn(),
    updateProduct: jest.fn(),
    deleteProduct: jest.fn(),
    createProductOption: jest.fn(),
    updateProductOption: jest.fn(),
    deleteProductOption: jest.fn(),
    createProductImage: jest.fn(),
  };

  const mockReviewsService = {
    getReviews: jest.fn(),
    createReview: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [ProductsController],
      providers: [
        {
          provide: ProductsService,
          useValue: mockProductsService,
        },
        {
          provide: ReviewsService,
          useValue: mockReviewsService,
        },
      ],
    }).compile();

    controller = module.get<ProductsController>(ProductsController);
    productsService = module.get<ProductsService>(ProductsService);
    reviewsService = module.get<ReviewsService>(ReviewsService);
  });

  describe('getProducts', () => {
    it('상품 목록을 조회하고 성공 응답을 반환해야 함', async () => {
      const query = {
        page: 1,
        per_page: 10,
      };

      const mockProducts = [
        {
          id: 1,
          name: '테스트 상품',
        },
      ];

      mockProductsService.getProducts.mockResolvedValue({
        items: mockProducts,
        total: 1,
      });

      const result = await controller.getProducts(query);

      expect(result).toEqual({
        success: true,
        message: '상품 목록을 성공적으로 조회했습니다.',
        data: {
          items: mockProducts,
          pagination: {
            current_page: 1,
            per_page: 10,
            total_items: 1,
            total_pages: 1,
          },
        },
      });
      expect(productsService.getProducts).toHaveBeenCalledWith(query);
    });
  });

  describe('getProduct', () => {
    it('상품 상세 정보를 조회하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const mockProduct = {
        id: productId,
        name: '테스트 상품',
      };

      mockProductsService.getProduct.mockResolvedValue(mockProduct);

      const result = await controller.getProduct(productId);

      expect(result).toEqual({
        success: true,
        message: '상품 상세 정보를 성공적으로 조회했습니다.',
        data: mockProduct,
      });
      expect(productsService.getProduct).toHaveBeenCalledWith(productId);
    });
  });

  describe('createProduct', () => {
    it('상품을 생성하고 성공 응답을 반환해야 함', async () => {
      const dto = {
        name: '테스트 상품',
        slug: 'test-product',
        shortDescription: '테스트 상품입니다.',
      };

      const mockProduct = {
        id: 1,
        ...dto,
      };

      mockProductsService.createProduct.mockResolvedValue(mockProduct);

      const result = await controller.createProduct(dto);

      expect(result).toEqual({
        success: true,
        message: '상품이 성공적으로 등록되었습니다.',
        data: mockProduct,
      });
      expect(productsService.createProduct).toHaveBeenCalledWith(dto);
    });
  });

  describe('deleteProduct', () => {
    it('상품을 삭제하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;

      mockProductsService.deleteProduct.mockResolvedValue(undefined);

      const result = await controller.deleteProduct(productId);

      expect(result).toEqual({
        success: true,
        message: '상품이 성공적으로 삭제되었습니다.',
        data: null,
      });
      expect(productsService.deleteProduct).toHaveBeenCalledWith(productId);
    });
  });

  describe('getReviews', () => {
    it('상품 리뷰를 조회하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const query = {
        page: 1,
        per_page: 10,
      };

      const mockReviews = {
        items: [
          {
            id: 1,
            rating: 5,
            content: '좋은 상품입니다.',
          },
        ],
        summary: {
          averageRating: 5,
          totalCount: 1,
          distribution: { '5': 1 },
        },
      };

      mockReviewsService.getReviews.mockResolvedValue(mockReviews);

      const result = await controller.getReviews(productId, query);

      expect(result).toEqual({
        success: true,
        message: '상품 리뷰를 성공적으로 조회했습니다.',
        data: {
          summary: mockReviews.summary,
          items: mockReviews.items,
          pagination: {
            current_page: 1,
            per_page: 10,
            total_items: mockReviews.summary.totalCount,
            total_pages: 1,
          },
        },
      });
      expect(reviewsService.getReviews).toHaveBeenCalledWith(productId, query);
    });
  });

  describe('updateProduct', () => {
    it('상품을 수정하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const dto = {
        name: '수정된 상품',
        slug: 'updated-product',
        shortDescription: '수정된 상품 설명',
      };

      const mockUpdatedProduct = {
        id: productId,
        ...dto,
      };

      mockProductsService.updateProduct.mockResolvedValue(mockUpdatedProduct);

      const result = await controller.updateProduct(productId, dto);

      expect(result).toEqual({
        success: true,
        message: '상품이 성공적으로 수정되었습니다.',
        data: mockUpdatedProduct,
      });
      expect(productsService.updateProduct).toHaveBeenCalledWith(
        productId,
        dto,
      );
    });
  });

  describe('createReview', () => {
    it('상품 리뷰를 생성하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const userId = 1;
      const dto = {
        rating: 5,
        title: '좋은 상품입니다',
        content: '상품이 마음에 듭니다.',
      };

      const mockReview = {
        id: 1,
        productId,
        userId,
        ...dto,
        createdAt: new Date(),
        updatedAt: new Date(),
      };

      mockReviewsService.createReview.mockResolvedValue(mockReview);

      const result = await controller.createReview(productId, userId, dto);

      expect(result).toEqual({
        success: true,
        message: '리뷰가 성공적으로 등록되었습니다.',
        data: mockReview,
      });
      expect(reviewsService.createReview).toHaveBeenCalledWith(
        productId,
        userId,
        dto,
      );
    });
  });

  describe('createProductOption', () => {
    it('상품 옵션을 생성하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const dto = {
        name: '색상',
        value: '빨강',
        price: 1000,
      };

      const mockOption = {
        id: 1,
        productId,
        ...dto,
      };

      mockProductsService.createProductOption.mockResolvedValue(mockOption);

      const result = await controller.createProductOption(productId, dto);

      expect(result).toEqual({
        success: true,
        message: '상품 옵션이 성공적으로 추가되었습니다.',
        data: mockOption,
      });
      expect(productsService.createProductOption).toHaveBeenCalledWith(
        productId,
        dto,
      );
    });
  });

  describe('updateProductOption', () => {
    it('상품 옵션을 수정하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const optionId = 1;
      const dto = {
        name: '색상',
        value: '파랑',
        price: 2000,
      };

      const mockUpdatedOption = {
        id: optionId,
        productId,
        ...dto,
      };

      mockProductsService.updateProductOption.mockResolvedValue(
        mockUpdatedOption,
      );

      const result = await controller.updateProductOption(
        productId,
        optionId,
        dto,
      );

      expect(result).toEqual({
        success: true,
        message: '상품 옵션이 성공적으로 수정되었습니다.',
        data: mockUpdatedOption,
      });
      expect(productsService.updateProductOption).toHaveBeenCalledWith(
        productId,
        optionId,
        dto,
      );
    });
  });

  describe('deleteProductOption', () => {
    it('상품 옵션을 삭제하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const optionId = 1;

      mockProductsService.deleteProductOption.mockResolvedValue(undefined);

      const result = await controller.deleteProductOption(productId, optionId);

      expect(result).toEqual({
        success: true,
        message: '상품 옵션이 성공적으로 삭제되었습니다.',
        data: null,
      });
      expect(productsService.deleteProductOption).toHaveBeenCalledWith(
        productId,
        optionId,
      );
    });
  });

  describe('createProductImage', () => {
    it('상품 이미지를 생성하고 성공 응답을 반환해야 함', async () => {
      const productId = 1;
      const dto = {
        url: 'https://example.com/image.jpg',
        alt: '상품 이미지',
      };

      const mockImage = {
        id: 1,
        productId,
        ...dto,
      };

      mockProductsService.createProductImage.mockResolvedValue(mockImage);

      const result = await controller.createProductImage(productId, dto);

      expect(result).toEqual({
        success: true,
        message: '상품 이미지가 성공적으로 추가되었습니다.',
        data: mockImage,
      });
      expect(productsService.createProductImage).toHaveBeenCalledWith(
        productId,
        dto,
      );
    });
  });
});
