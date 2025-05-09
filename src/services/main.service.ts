import { productRepository } from '../repositories/product.repository';
import { categoryRepository } from '../repositories/category.repository';
import { ProductListItemResponse, FeaturedCategoryResponse } from '../types';
import { ProductSortParams } from '../types/product.types';
import { prisma } from '../lib/prisma';

export const mainService = {
  /**
   * 신규 상품 목록 조회
   * @param limit - 조회할 상품 수 (기본값: 5)
   * @returns 신규 상품 목록
   */
  async getNewProducts(limit = 5): Promise<ProductListItemResponse[]> {
    const sorts: ProductSortParams[] = [{ field: 'createdAt', direction: 'desc' }];
    return productRepository.findAll(0, limit, undefined, sorts);
  },

  /**
   * 인기 상품 목록 조회
   * @param limit - 조회할 상품 수 (기본값: 5)
   * @returns 인기 상품 목록
   */
  async getPopularProducts(limit = 5): Promise<ProductListItemResponse[]> {
    // 리뷰 수가 많은 상품 순서로 조회
    const sorts: ProductSortParams[] = [{ field: 'reviewCount', direction: 'desc' }];
    return productRepository.findAll(0, limit, undefined, sorts);
  },

  /**
   * 주요 카테고리 목록 조회
   * @param limit - 조회할 카테고리 수 (기본값: 5)
   * @returns 주요 카테고리 목록
   */
  async getFeaturedCategories(limit = 5): Promise<FeaturedCategoryResponse[]> {
    const categories = await categoryRepository.findAllRoot();

    /**
     * 카테고리를 API 응답 형식으로 변환
     */
    const featuredCategories = await Promise.all(
      categories.slice(0, limit).map(async (category) => {
        // 각 카테고리에 속한 상품 수 계산
        const productCount = await prisma.product.count({
          where: {
            categories: {
              some: {
                categoryId: category.id,
              },
            },
          },
        });

        return {
          id: category.id,
          name: category.name,
          slug: category.slug,
          image_url: category.imageUrl || undefined,
          product_count: productCount,
        };
      }),
    );

    return featuredCategories;
  },
};
