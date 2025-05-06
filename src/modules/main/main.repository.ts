import { Injectable } from '@nestjs/common';
import { desc, eq, sql } from 'drizzle-orm';

import { DrizzleService } from '~/database/drizzle.service';
import {
  products as productsSchema,
  categories as categoriesSchema,
  productCategories as productCategoriesSchema,
  productImages as productImagesSchema,
  reviews as reviewsSchema,
} from '~/database/schema';
import { ProductWithRelations } from '../products/entities/product.entity';

@Injectable()
export class MainRepository {
  constructor(private readonly drizzleService: DrizzleService) {}

  async getNewProducts() {
    const products = await this.drizzleService.db.query.products.findMany({
      with: this.getProductRelations(),
      where: eq(productsSchema.status, 'ACTIVE'),
      orderBy: desc(productsSchema.createdAt),
      limit: 10,
    });

    return products.map((product) => this.mapProductResponse(product));
  }

  async getPopularProducts() {
    const products = await this.drizzleService.db.query.products.findMany({
      with: this.getProductRelations(),
      where: eq(productsSchema.status, 'ACTIVE'),
      orderBy: [
        desc(sql`(
          SELECT AVG(rating) 
          FROM ${reviewsSchema} 
          WHERE product_id = ${productsSchema.id}
        )`),
        desc(sql`(
          SELECT COUNT(*) 
          FROM ${reviewsSchema} 
          WHERE product_id = ${productsSchema.id}
        )`),
      ],
      limit: 10,
    });

    return products.map((product) => this.mapProductResponse(product));
  }

  async getFeaturedCategories() {
    const categories = await this.drizzleService.db.query.categories.findMany({
      where: eq(categoriesSchema.level, 1),
      columns: {
        id: true,
        name: true,
        slug: true,
        imageUrl: true,
      },
      limit: 6,
    });

    const categoriesWithProductCount = await Promise.all(
      categories.map(async (category) => {
        const result = await this.drizzleService.db
          .select({ count: sql`count(*)` })
          .from(productCategoriesSchema)
          .where(eq(productCategoriesSchema.categoryId, category.id));

        return {
          ...category,
          productCount: Number(result[0].count),
        };
      }),
    );

    return categoriesWithProductCount;
  }

  private getProductRelations() {
    return {
      brand: {
        columns: {
          id: true,
          name: true,
          logoUrl: true,
        },
      },
      seller: {
        columns: {
          id: true,
          name: true,
          logoUrl: true,
        },
      },
      images: {
        where: eq(productImagesSchema.isPrimary, true),
        columns: {
          url: true,
          altText: true,
        },
      },
      prices: {
        columns: {
          basePrice: true,
          salePrice: true,
          currency: true,
        },
      },
      reviews: {
        columns: {
          rating: true,
        },
      },
      optionGroups: {
        with: {
          options: {
            columns: {
              stock: true,
            },
          },
        },
      },
    };
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

  private mapProductResponse(product: ProductWithRelations) {
    const stats = this.calculateProductStats(product);
    return {
      ...product,
      primaryImage: product.images[0] || null,
      basePrice: product.price?.basePrice || 0,
      salePrice: product.price?.salePrice || 0,
      currency: product.price?.currency || 'KRW',
      ...stats,
    };
  }
}
