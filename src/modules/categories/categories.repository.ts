import { Injectable } from '@nestjs/common';
import { eq, inArray, sql } from 'drizzle-orm';

import { DrizzleService } from '~/database/drizzle.service';
import { getOrderBy } from '~/common/utils/drizzle-helpers';
import {
  categories as categoriesSchema,
  productCategories as productCategoriesSchema,
  products as productsSchema,
} from '~/database/schema';
import { GetProductsByCategoryIdRequestDto } from './dto/category.dto';
import { ProductWithRelations } from '../products/entities/product.entity';
import { CategoryWithRelations } from './entities/category.entity';

@Injectable()
export class CategoryRepository {
  constructor(private readonly drizzleService: DrizzleService) {}

  async getCategories(level?: number): Promise<CategoryWithRelations[]> {
    const categories = await this.drizzleService.db.query.categories.findMany({
      with: !level
        ? {
            children: {
              with: {
                children: true,
              },
            },
          }
        : undefined,
      where: level ? eq(categoriesSchema.level, level) : undefined,
    });

    return categories;
  }

  async getCategoryById(id: number): Promise<CategoryWithRelations> {
    const category = await this.drizzleService.db.query.categories.findFirst({
      with: {
        children: {
          with: {
            children: true,
          },
        },
      },
      where: eq(categoriesSchema.id, id),
    });

    return category;
  }

  async getProductsCountByCategoryId(
    categoryId: number,
    query: GetProductsByCategoryIdRequestDto,
  ): Promise<number> {
    let categoryIds: number[] = [categoryId];
    if (query.includeSubCategories) {
      categoryIds = await this.getSubCategories(categoryId);
    }

    const productIds = await this.drizzleService.db
      .select({ productId: productCategoriesSchema.productId })
      .from(productCategoriesSchema)
      .where(inArray(productCategoriesSchema.categoryId, categoryIds))
      .groupBy(productCategoriesSchema.productId);

    const result = await this.drizzleService.db
      .select({ count: sql`count(*)` })
      .from(productsSchema)
      .where(
        inArray(
          productsSchema.id,
          productIds.map((p) => p.productId),
        ),
      );

    return Number(result[0].count);
  }

  async getProductsByCategoryId(
    categoryId: number,
    query: GetProductsByCategoryIdRequestDto,
  ): Promise<ProductWithRelations[]> {
    const {
      page = 1,
      per_page = 10,
      sort = 'created_at:desc',
      includeSubCategories = true,
    } = query;
    const offset = (page - 1) * per_page;

    let categoryIds: number[] = [categoryId];
    if (includeSubCategories) {
      categoryIds = await this.getSubCategories(categoryId);
    }

    const productIds = await this.drizzleService.db
      .select({ productId: productCategoriesSchema.productId })
      .from(productCategoriesSchema)
      .where(inArray(productCategoriesSchema.categoryId, categoryIds))
      .groupBy(productCategoriesSchema.productId);

    const products = await this.drizzleService.db.query.products.findMany({
      with: {
        categories: true,
      },
      where: inArray(
        productsSchema.id,
        productIds.map((p) => p.productId),
      ),
      orderBy: getOrderBy('product', sort),
      limit: per_page,
      offset: offset,
    });

    return products;
  }

  private async getSubCategories(categoryId: number): Promise<number[]> {
    const subcategories = await this.drizzleService.db.execute(
      sql`
            WITH RECURSIVE category_tree AS (
                SELECT id FROM categories WHERE id = ${categoryId}
                UNION ALL
                SELECT c.id FROM categories c
                INNER JOIN category_tree ct ON c.parent_id = ct.id
            )
            SELECT id FROM category_tree
        `,
    );
    return subcategories.rows.map((row) => Number(row.id));
  }
}
