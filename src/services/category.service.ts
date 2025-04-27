import { Prisma } from '@prisma/client';
import { categoryRepository } from '../repositories/category.repository';
import { getCategoryIds, isCategoryWithChildren } from '../utils/category';
import { PaginationParams } from '../utils/pagination';
import {
  CategoryWithChildrenResponse,
  ProductListItemResponse,
  CategoryBaseResponse,
} from '../types';
import { productRepository } from '../repositories/product.repository';
import { mapProductToListItem, ProductWithRelations } from '../types/mappers/product.mapper';
import {
  mapCategoryToResponse,
  mapCategoryToBasicResponse,
  CategoryWithRelations,
} from '../types/mappers/category.mapper';
import {
  FeaturedCategoryResponse,
  CategoryProductsResponse,
} from '../types/responses/category.response';
import { prisma } from '../lib/prisma';

interface CategoryProductsResult {
  category: CategoryWithChildrenResponse;
  products: ProductListItemResponse[];
  total: number;
  page: number;
  perPage: number;
  totalPages: number;
}

// 추가: 타입 가드 함수
function isCategoryWithRelations(category: unknown): category is CategoryWithRelations {
  if (!category || typeof category !== 'object') return false;

  const c = category;
  return (
    'id' in c &&
    typeof c.id === 'number' &&
    'name' in c &&
    typeof c.name === 'string' &&
    'slug' in c &&
    typeof c.slug === 'string'
  );
}

function isProductWithRelations(product: unknown): product is ProductWithRelations {
  if (!product || typeof product !== 'object') return false;

  const p = product;
  return (
    'id' in p &&
    typeof p.id === 'number' &&
    'name' in p &&
    typeof p.name === 'string' &&
    'slug' in p &&
    typeof p.slug === 'string' &&
    'images' in p &&
    Array.isArray(p.images)
  );
}

// 안전한 매핑 함수
function safeMapCategories(categories: unknown[]): CategoryWithChildrenResponse[] {
  return categories
    .filter(isCategoryWithRelations)
    .map((category) => mapCategoryToResponse(category));
}

function safeMapProducts(products: unknown[]): ProductListItemResponse[] {
  return products.filter(isProductWithRelations).map((product) => mapProductToListItem(product));
}

export const categoryService = {
  /**
   * 모든 카테고리 조회
   * @param level 카테고리 레벨 필터 (선택사항)
   */
  async getAllCategories(level?: number): Promise<CategoryWithChildrenResponse[]> {
    const categories = await categoryRepository.findAll(level);

    // DB 모델을 API 응답 형식으로 변환
    return safeMapCategories(categories);
  },

  /**
   * 특정 카테고리의 상품 목록 조회
   */
  async getCategoryProducts(
    categoryId: number,
    { page, perPage }: PaginationParams,
    includeSubcategories: boolean,
  ): Promise<CategoryProductsResult> {
    // 카테고리 조회
    const category = await categoryRepository.findById(categoryId);

    if (!category) {
      throw new Error('카테고리를 찾을 수 없습니다.');
    }

    // 안전하게 타입 확인
    if (!isCategoryWithRelations(category)) {
      throw new Error(`카테고리 ID ${categoryId}에 필요한 관계 정보가 없습니다`);
    }

    const categoryWithRelations = category;

    // 카테고리 ID 목록 준비
    const childCategories = await prisma.category.findMany({
      where: { parentId: categoryId },
    });
    const categoryIds = includeSubcategories
      ? [categoryId, ...childCategories.map((childCat) => childCat.id)]
      : [categoryId];

    // 전체 상품 수 계산
    const productCategoriesWhere = {
      categoryId: { in: categoryIds },
    };
    const total = await prisma.productCategory.count({
      where: productCategoriesWhere,
    });

    // 상품 조회
    const skip = (page - 1) * perPage;
    const productCategories = await prisma.productCategory.findMany({
      where: productCategoriesWhere,
      select: {
        product: {
          include: {
            brand: true,
            seller: true,
            price: true,
            images: true,
            optionGroups: {
              include: {
                options: true,
              },
            },
          },
        },
      },
      skip,
      take: perPage,
    });

    const products = productCategories.map((pc) => pc.product);

    // DB 모델을 API 응답 형식으로 변환
    const productResponses = products
      .filter((product) => isProductWithRelations(product))
      .map((product) => mapProductToListItem(product));

    // 카테고리 변환
    const categoryResponse = mapCategoryToResponse(categoryWithRelations);

    return {
      category: categoryResponse,
      products: productResponses,
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage),
    };
  },

  /**
   * 카테고리 목록 조회
   */
  async getCategories(level?: number): Promise<CategoryWithChildrenResponse[]> {
    const categories = await categoryRepository.findAll(level);

    // DB 모델을 API 응답 형식으로 변환
    return safeMapCategories(categories);
  },

  /**
   * 카테고리 상세 조회
   */
  async getCategoryById(id: number): Promise<CategoryWithChildrenResponse> {
    const category = await categoryRepository.findById(id);

    if (!category) {
      throw new Error(`카테고리 ID ${id}를 찾을 수 없습니다`);
    }

    // 타입 안전성 확인
    if (!isCategoryWithRelations(category)) {
      throw new Error(`카테고리 ID ${id}에 필요한 관계 정보가 없습니다`);
    }

    // DB 모델을 API 응답 형식으로 변환
    return mapCategoryToResponse(category);
  },

  /**
   * 카테고리 생성
   */
  async createCategory(data: Prisma.CategoryCreateInput): Promise<CategoryWithChildrenResponse> {
    const newCategory = await prisma.category.create({ data });

    // 생성된 카테고리 전체 정보 조회
    const category = await categoryRepository.findById(newCategory.id);
    if (!category) {
      throw new Error(`생성된 카테고리 ID ${newCategory.id}를 찾을 수 없습니다`);
    }

    if (!isCategoryWithRelations(category)) {
      throw new Error(`생성된 카테고리 ID ${newCategory.id}에 필요한 관계 정보가 없습니다`);
    }

    return mapCategoryToResponse(category);
  },

  /**
   * 카테고리 수정
   */
  async updateCategory(
    id: number,
    data: Prisma.CategoryUpdateInput,
  ): Promise<CategoryWithChildrenResponse> {
    // 카테고리 존재 여부 확인
    const existingCategory = await categoryRepository.findById(id);

    if (!existingCategory) {
      throw new Error(`카테고리 ID ${id}를 찾을 수 없습니다`);
    }

    // 카테고리 수정
    await prisma.category.update({
      where: { id },
      data,
    });

    // 업데이트된 카테고리 정보 조회
    const updatedCategory = await categoryRepository.findById(id);
    if (!updatedCategory) {
      throw new Error(`업데이트된 카테고리 ID ${id}를 찾을 수 없습니다`);
    }

    if (!isCategoryWithRelations(updatedCategory)) {
      throw new Error(`업데이트된 카테고리 ID ${id}에 필요한 관계 정보가 없습니다`);
    }

    return mapCategoryToResponse(updatedCategory);
  },

  /**
   * 카테고리 삭제
   */
  async deleteCategory(id: number): Promise<void> {
    // 카테고리 존재 여부 확인
    const existingCategory = await categoryRepository.findById(id);

    if (!existingCategory) {
      throw new Error(`카테고리 ID ${id}를 찾을 수 없습니다`);
    }

    // 하위 카테고리 존재 여부 확인
    const childCategories = await prisma.category.findMany({
      where: { parentId: id },
    });

    if (childCategories.length > 0) {
      throw new Error('하위 카테고리가 있는 카테고리는 삭제할 수 없습니다');
    }

    // 카테고리 삭제
    await prisma.category.delete({
      where: { id },
    });
  },

  /**
   * 메인 페이지 추천 카테고리 목록 조회
   */
  async getFeaturedCategories(): Promise<FeaturedCategoryResponse[]> {
    // 특정 조건(예: level=1)으로 카테고리 조회
    const categories = await prisma.category.findMany({
      where: { level: 1 },
      orderBy: { id: 'asc' },
      take: 6,
    });

    return Promise.all(
      categories.map(async (category) => {
        // 해당 카테고리에 속한 상품 수 계산
        const productCount = await prisma.productCategory.count({
          where: { categoryId: category.id },
        });

        return {
          id: category.id,
          name: category.name,
          slug: category.slug,
          image_url: category.imageUrl ?? undefined,
          product_count: productCount,
        };
      }),
    );
  },
};
