import { Injectable, NotFoundException } from '@nestjs/common';
import { ProductsRepository } from './products.repository';
import {
  CraeteProductResponseData,
  CreateProductRequestDto,
  GetProductListResponseData,
  GetProductResponseData,
  GetProductsRequestDto,
  UpdateProductRequestDto,
  UpdateProductResponseData,
} from './dto/product.dto';
import { ProductWithRelations } from './entities/product.entity';

@Injectable()
export class ProductsService {
  constructor(private readonly productsRepository: ProductsRepository) {}

  async getProducts(params: GetProductsRequestDto): Promise<{
    items: GetProductListResponseData[];
    total: number;
  }> {
    const [items, total] = await Promise.all([
      this.productsRepository.getProducts(params),
      this.productsRepository.getProductsCount(params),
    ]);
    return { items, total };
  }

  async getProduct(id: number): Promise<GetProductResponseData> {
    const product = await this.productsRepository.getProduct(id);
    if (!product) {
      throw new NotFoundException('요청한 상품을 찾을 수 없습니다.');
    }
    return this.mapProductDetailResponse(product);
  }

  async createProduct(
    data: CreateProductRequestDto,
  ): Promise<CraeteProductResponseData> {
    return this.productsRepository.createProduct(data);
  }

  async updateProduct(
    id: number,
    data: UpdateProductRequestDto,
  ): Promise<UpdateProductResponseData> {
    const product = await this.productsRepository.getProduct(id);
    if (!product) {
      throw new NotFoundException('요청한 상품을 찾을 수 없습니다.');
    }
    return this.productsRepository.updateProduct(id, data);
  }

  async deleteProduct(id: number): Promise<void> {
    return this.productsRepository.deleteProduct(id);
  }

  private calculateProductStats(product: ProductWithRelations) {
    const totalRating = product.reviews.reduce(
      (sum, review) => sum + review.rating,
      0,
    );
    const averageRating =
      product.reviews.length > 0 ? totalRating / product.reviews.length : 0;
    const totalStock = product.optionGroups.reduce(
      (sum, group) =>
        sum +
        group.options.reduce((groupSum, option) => groupSum + option.stock, 0),
      0,
    );

    return {
      rating: averageRating,
      reviewCount: product.reviews.length,
      inStock: totalStock > 0,
    };
  }

  private mapProductDetailResponse(product: ProductWithRelations) {
    const stats = this.calculateProductStats(product);
    const discountPercentage = product.price.salePrice
      ? Math.round(
          ((product.price.basePrice - product.price.salePrice) /
            product.price.basePrice) *
            100,
        )
      : null;

    return {
      ...product,
      rating: {
        average: stats.rating,
        count: stats.reviewCount,
        distribution: product.reviews.reduce(
          (acc: Record<string, number>, review: any) => {
            acc[review.rating] = (acc[review.rating] || 0) + 1;
            return acc;
          },
          {},
        ),
      },
      price: {
        ...product.price,
        discountPercentage,
      },
    };
  }
}
