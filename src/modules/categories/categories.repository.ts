import { Injectable } from '@nestjs/common';
import { asc, desc, eq, inArray, sql } from 'drizzle-orm';
import { DrizzleService } from '~/database/drizzle.service';
import {
  categories as categoriesSchema,
  productCategories as productCategoriesSchema,
  products as productsSchema,
} from '~/database/schema';
import { GetProductsByCategoryIdRequestDto } from './dto/category.dto';

@Injectable()
export class CategoryRepository {
  constructor(private readonly drizzleService: DrizzleService) {}

  async getCategories(level?: number) {
    const categories = await this.drizzleService.db.query.categories.findMany({
      with: {
        children: {
          with: {
            children: true,
          },
        },
      },
      where: eq(categoriesSchema.level, level),
    });
    return categories;
  }

  async getProductsByCategoryId(
    categoryId: number,
    query: GetProductsByCategoryIdRequestDto,
  ) {
    const {
      page = 1,
      per_page = 10,
      sort = 'created_at:desc',
      includeSubCategories = true,
    } = query;
    const offset = (page - 1) * per_page;

    let categoryIds: number[] = [categoryId];
    if (includeSubCategories) {
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
      categoryIds = subcategories.rows.map((row) => Number(row.id));
    }

    const products = await this.drizzleService.db.query.products.findMany({
      with: {
        categories: {
          where: inArray(productCategoriesSchema.categoryId, categoryIds),
        },
      },
      orderBy: this.getOrderBy(sort),
      limit: per_page,
      offset: offset,
    });

    return products;
  }

  private getOrderBy(sort: GetProductsByCategoryIdRequestDto['sort']) {
    const fieldMap = {
      created_at: productsSchema.createdAt,
    };
    const [field, order] = sort.split(':');

    if (!fieldMap[field]) {
      return desc(productsSchema.createdAt);
    }
    return order === 'desc' ? desc(fieldMap[field]) : asc(fieldMap[field]);
  }
}
