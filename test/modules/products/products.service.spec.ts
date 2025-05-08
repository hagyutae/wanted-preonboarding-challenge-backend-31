import { Test, TestingModule } from '@nestjs/testing';
import { ProductsService } from '~/modules/products/products.service';
import { ProductsRepository } from '~/modules/products/products.repository';
import { NotFoundException } from '@nestjs/common';
import { CreateProductOptionRequestDto } from '~/modules/products/dto/product-option.dto';

describe('ProductsService', () => {
  let service: ProductsService;
  let repository: ProductsRepository;

  const mockProductsRepository = {
    getProduct: jest.fn(),
    updateProduct: jest.fn(),
    createProductOption: jest.fn(),
    updateProductOption: jest.fn(),
    deleteProductOption: jest.fn(),
    createProductImage: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        ProductsService,
        {
          provide: ProductsRepository,
          useValue: mockProductsRepository,
        },
      ],
    }).compile();

    service = module.get<ProductsService>(ProductsService);
    repository = module.get<ProductsRepository>(ProductsRepository);
  });

  describe('updateProduct', () => {
    it('상품이 존재하지 않으면 NotFoundException을 던져야 함', async () => {
      const productId = 1;
      const dto = {
        name: '수정된 상품',
        slug: 'updated-product',
        shortDescription: '수정된 상품 설명',
      };

      mockProductsRepository.getProduct.mockResolvedValue(null);

      await expect(service.updateProduct(productId, dto)).rejects.toThrow(
        NotFoundException,
      );
    });

    it('상품이 존재하면 수정해야 함', async () => {
      const productId = 1;
      const dto = {
        name: '수정된 상품',
        slug: 'updated-product',
        shortDescription: '수정된 상품 설명',
      };

      const mockProduct = { id: 1, name: '테스트 상품' };
      const mockUpdatedProduct = {
        id: productId,
        ...dto,
      };

      mockProductsRepository.getProduct.mockResolvedValue(mockProduct);
      mockProductsRepository.updateProduct.mockResolvedValue(
        mockUpdatedProduct,
      );

      const result = await service.updateProduct(productId, dto);

      expect(result).toEqual(mockUpdatedProduct);
      expect(repository.updateProduct).toHaveBeenCalledWith(productId, dto);
    });
  });

  describe('createProductOption', () => {
    it('상품이 존재하지 않으면 NotFoundException을 던져야 함', async () => {
      const productId = 1;
      const dto: CreateProductOptionRequestDto = {
        optionGroupId: 1,
        name: '테스트 옵션',
        additionalPrice: 1000,
        sku: 'TEST-SKU',
        stock: 10,
        displayOrder: 1,
      };

      mockProductsRepository.getProduct.mockResolvedValue(null);

      await expect(service.createProductOption(productId, dto)).rejects.toThrow(
        NotFoundException,
      );
    });

    it('상품이 존재하면 옵션을 생성해야 함', async () => {
      const productId = 1;
      const dto: CreateProductOptionRequestDto = {
        optionGroupId: 1,
        name: '테스트 옵션',
        additionalPrice: 1000,
        sku: 'TEST-SKU',
        stock: 10,
        displayOrder: 1,
      };

      const mockProduct = { id: 1, name: '테스트 상품' };
      const mockOption = {
        id: 1,
        optionGroupId: 1,
        name: '테스트 옵션',
        additionalPrice: 1000,
        sku: 'TEST-SKU',
        stock: 10,
        displayOrder: 1,
      };

      mockProductsRepository.getProduct.mockResolvedValue(mockProduct);
      mockProductsRepository.createProductOption.mockResolvedValue(mockOption);

      const result = await service.createProductOption(productId, dto);

      expect(result).toEqual(mockOption);
      expect(repository.createProductOption).toHaveBeenCalledWith(dto);
    });
  });

  describe('updateProductOption', () => {
    it('상품이 존재하지 않으면 NotFoundException을 던져야 함', async () => {
      const productId = 1;
      const optionId = 1;
      const dto = {
        name: '수정된 옵션',
        additionalPrice: 2000,
        sku: 'TEST-SKU-2',
        stock: 20,
        displayOrder: 2,
      };

      mockProductsRepository.getProduct.mockResolvedValue(null);

      await expect(
        service.updateProductOption(productId, optionId, dto),
      ).rejects.toThrow(NotFoundException);
    });

    it('상품이 존재하면 옵션을 수정해야 함', async () => {
      const productId = 1;
      const optionId = 1;
      const dto = {
        name: '수정된 옵션',
        additionalPrice: 2000,
        sku: 'TEST-SKU-2',
        stock: 20,
        displayOrder: 2,
      };

      const mockProduct = { id: 1, name: '테스트 상품' };
      const mockUpdatedOption = {
        id: 1,
        optionGroupId: 1,
        ...dto,
      };

      mockProductsRepository.getProduct.mockResolvedValue(mockProduct);
      mockProductsRepository.updateProductOption.mockResolvedValue(
        mockUpdatedOption,
      );

      const result = await service.updateProductOption(
        productId,
        optionId,
        dto,
      );

      expect(result).toEqual(mockUpdatedOption);
      expect(repository.updateProductOption).toHaveBeenCalledWith(
        optionId,
        dto,
      );
    });
  });

  describe('deleteProductOption', () => {
    it('상품이 존재하지 않으면 NotFoundException을 던져야 함', async () => {
      const productId = 1;
      const optionId = 1;

      mockProductsRepository.getProduct.mockResolvedValue(null);

      await expect(
        service.deleteProductOption(productId, optionId),
      ).rejects.toThrow(NotFoundException);
    });

    it('상품이 존재하면 옵션을 삭제해야 함', async () => {
      const productId = 1;
      const optionId = 1;

      const mockProduct = { id: 1, name: '테스트 상품' };

      mockProductsRepository.getProduct.mockResolvedValue(mockProduct);
      mockProductsRepository.deleteProductOption.mockResolvedValue(undefined);

      await service.deleteProductOption(productId, optionId);

      expect(repository.deleteProductOption).toHaveBeenCalledWith(optionId);
    });
  });

  describe('createProductImage', () => {
    it('상품이 존재하지 않으면 NotFoundException을 던져야 함', async () => {
      const productId = 1;
      const dto = {
        url: 'https://example.com/image.jpg',
        alt: '상품 이미지',
      };

      mockProductsRepository.getProduct.mockResolvedValue(null);

      await expect(service.createProductImage(productId, dto)).rejects.toThrow(
        NotFoundException,
      );
    });

    it('상품이 존재하면 이미지를 생성해야 함', async () => {
      const productId = 1;
      const dto = {
        url: 'https://example.com/image.jpg',
        alt: '상품 이미지',
      };

      const mockProduct = { id: 1, name: '테스트 상품' };
      const mockImage = {
        id: 1,
        productId,
        ...dto,
      };

      mockProductsRepository.getProduct.mockResolvedValue(mockProduct);
      mockProductsRepository.createProductImage.mockResolvedValue(mockImage);

      const result = await service.createProductImage(productId, dto);

      expect(result).toEqual(mockImage);
      expect(repository.createProductImage).toHaveBeenCalledWith(
        productId,
        dto,
      );
    });
  });
});
