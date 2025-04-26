import { Prisma } from '@prisma/client';
import { productRepository } from '../repositories/product.repository';
import { PaginationParams } from '../utils/pagination';

export const productService = {
  /**
   * 상품 목록 조회
   */
  async getProducts({ page, perPage }: PaginationParams) {
    const skip = (page - 1) * perPage;
    const take = perPage;

    const [products, total] = await Promise.all([
      productRepository.findAll(skip, take),
      productRepository.count()
    ]);

    return {
      products,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage)
    };
  },

  /**
   * 상품 상세 조회
   */
  async getProductById(id: number) {
    const product = await productRepository.findById(id);

    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    return product;
  },

  /**
   * 상품 생성
   */
  async createProduct(data: Prisma.ProductCreateInput) {
    return productRepository.create(data);
  },

  /**
   * 상품 수정
   */
  async updateProduct(id: number, data: Prisma.ProductUpdateInput) {
    const product = await productRepository.findById(id);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    return productRepository.update(id, data);
  },

  /**
   * 상품 삭제
   */
  async deleteProduct(id: number) {
    const product = await productRepository.findById(id);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    return productRepository.delete(id);
  },

  /**
   * 상품 옵션 추가
   */
  async addProductOption(productId: number, optionGroupId: number, optionData: Prisma.ProductOptionCreateInput) {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    return productRepository.addOption(productId, optionGroupId, optionData);
  },

  /**
   * 상품 옵션 수정
   */
  async updateProductOption(productId: number, optionId: number, data: Prisma.ProductOptionUpdateInput) {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    return productRepository.updateOption(optionId, data);
  },

  /**
   * 상품 옵션 삭제
   */
  async deleteProductOption(productId: number, optionId: number) {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    return productRepository.deleteOption(optionId);
  },

  /**
   * 상품 이미지 추가
   */
  async addProductImage(productId: number, imageData: Prisma.ProductImageCreateInput) {
    const product = await productRepository.findById(productId);
    
    if (!product) {
      throw new Error('상품을 찾을 수 없습니다.');
    }

    return productRepository.addImage(productId, imageData);
  },
}; 