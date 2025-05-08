import { Injectable } from '@nestjs/common';
import { DrizzleService } from '~/database/drizzle.service';
import { and, eq, like, lte, or, sql, gte } from 'drizzle-orm';
import { getOrderBy } from '~/common/utils/drizzle-helpers';

import {
  products as productsSchema,
  productCategories as productCategoriesSchema,
  productImages as productImagesSchema,
  productOptionGroups as productOptionGroupsSchema,
  productOptions as productOptionsSchema,
  productPrices as productPricesSchema,
  productDetails as productDetailsSchema,
  productTags as productTagsSchema,
} from '~/database/schema';
import {
  CreateProductRequestDto,
  GetProductsRequestDto,
  UpdateProductRequestDto,
  CraeteProductResponseData,
  UpdateProductResponseData,
} from './dto/product.dto';
import { ProductWithRelations } from './entities/product.entity';
import {
  CreateProductOptionRequestDto,
  CreateProductOptionResponseData,
  CreateProductOptionResponseDto,
  UpdateProductOptionRequestDto,
  UpdateProductOptionResponseData,
  UpdateProductOptionResponseDto,
} from './dto/product-option.dto';
import {
  CreateProductImageRequestDto,
  CreateProductImageResponseData,
  CreateProductImageResponseDto,
} from './dto/product-image.dto';

@Injectable()
export class ProductsRepository {
  constructor(private readonly drizzleService: DrizzleService) {}

  async getProducts(
    params: GetProductsRequestDto,
  ): Promise<ProductWithRelations[]> {
    const {
      page = 1,
      per_page: perPage = 10,
      sort = 'created_at:desc',
      ...filters
    } = params;
    const offset = (page - 1) * perPage;

    const where = this.buildProductQueryConditions(filters);

    return this.drizzleService.db.query.products.findMany({
      with: this.getProductListRelations(),
      where,
      orderBy: getOrderBy('product', sort),
      limit: perPage,
      offset,
    });
  }

  async getProductsCount(params: GetProductsRequestDto): Promise<number> {
    const where = this.buildProductQueryConditions(params);

    const result = await this.drizzleService.db
      .select({ count: sql`count(*)` })
      .from(productsSchema)
      .where(where)
      .then((result) => Number(result[0].count));

    return result;
  }

  async getProduct(id: number): Promise<ProductWithRelations> {
    const product = await this.drizzleService.db.query.products.findFirst({
      with: this.getProductDetailRelations(),
      where: eq(productsSchema.id, id),
    });

    return product;
  }

  async createProduct(
    data: CreateProductRequestDto,
  ): Promise<CraeteProductResponseData> {
    const {
      categories,
      optionGroups,
      images,
      detail,
      price,
      tags,
      ...productData
    } = data;

    const [product] = await this.drizzleService.db.transaction(async (tx) => {
      const [newProduct] = await tx
        .insert(productsSchema)
        .values({
          id: undefined,
          name: productData.name || '',
          slug: productData.slug || '',
          shortDescription: productData.shortDescription || '',
          fullDescription: productData.fullDescription || '',
          status: productData.status || 'ACTIVE',
          sellerId: productData.sellerId,
          brandId: productData.brandId,
          createdAt: new Date(),
          updatedAt: new Date(),
        })
        .returning();

      if (detail) {
        await tx.insert(productDetailsSchema).values({
          id: undefined,
          productId: newProduct.id,
          weight: String(detail.weight),
          dimensions: detail.dimensions,
          materials: detail.materials,
          countryOfOrigin: detail.countryOfOrigin,
          warrantyInfo: detail.warrantyInfo,
          careInstructions: detail.careInstructions,
          additionalInfo: detail.additionalInfo,
        });
      }

      if (price) {
        await tx.insert(productPricesSchema).values({
          id: undefined,
          productId: newProduct.id,
          basePrice: String(price.basePrice),
          salePrice: price.salePrice ? String(price.salePrice) : undefined,
          costPrice: price.costPrice ? String(price.costPrice) : undefined,
          currency: price.currency,
          taxRate: price.taxRate ? String(price.taxRate) : undefined,
        });
      }

      if (categories?.length) {
        await tx.insert(productCategoriesSchema).values(
          categories.map((cat: any) => ({
            id: undefined,
            productId: newProduct.id,
            categoryId: cat.categoryId,
            isPrimary: cat.isPrimary,
          })),
        );
      }

      if (optionGroups?.length) {
        for (const group of optionGroups) {
          const [newGroup] = await tx
            .insert(productOptionGroupsSchema)
            .values({
              id: undefined,
              name: group.name,
              productId: newProduct.id,
              displayOrder: group.displayOrder,
            })
            .returning();

          if (group.options?.length) {
            await tx.insert(productOptionsSchema).values(
              group.options.map((option: any) => ({
                id: undefined,
                optionGroupId: newGroup.id,
                name: option.name,
                additionalPrice: option.additionalPrice,
                sku: option.sku,
                stock: option.stock,
                displayOrder: option.displayOrder,
              })),
            );
          }
        }
      }

      if (images?.length) {
        await tx.insert(productImagesSchema).values(
          images.map((img: any) => ({
            id: undefined,
            productId: newProduct.id,
            url: img.url,
            altText: img.altText,
            isPrimary: img.isPrimary,
            displayOrder: img.displayOrder,
            optionId: img.optionId,
          })),
        );
      }

      if (tags?.length) {
        await tx.insert(productTagsSchema).values(
          tags.map((tagId: number) => ({
            id: undefined,
            productId: newProduct.id,
            tagId,
          })),
        );
      }

      return [newProduct];
    });

    return product;
  }

  async updateProduct(
    id: number,
    data: UpdateProductRequestDto,
  ): Promise<UpdateProductResponseData> {
    const {
      categories,
      optionGroups,
      images,
      detail,
      price,
      tags,
      ...productData
    } = data;

    const [product] = await this.drizzleService.db.transaction(async (tx) => {
      const [updatedProduct] = await tx
        .update(productsSchema)
        .set({ ...productData, updatedAt: new Date() })
        .where(eq(productsSchema.id, id))
        .returning();

      if (detail) {
        await tx.insert(productDetailsSchema).values({
          id: undefined,
          productId: updatedProduct.id,
          weight: String(detail.weight),
          dimensions: detail.dimensions,
          materials: detail.materials,
          countryOfOrigin: detail.countryOfOrigin,
          warrantyInfo: detail.warrantyInfo,
          careInstructions: detail.careInstructions,
          additionalInfo: detail.additionalInfo,
        });
      }

      if (price) {
        await tx.insert(productPricesSchema).values({
          id: undefined,
          productId: updatedProduct.id,
          basePrice: String(price.basePrice),
          salePrice: price.salePrice ? String(price.salePrice) : undefined,
          costPrice: price.costPrice ? String(price.costPrice) : undefined,
          currency: price.currency,
          taxRate: price.taxRate ? String(price.taxRate) : undefined,
        });
      }

      if (categories) {
        await tx
          .delete(productCategoriesSchema)
          .where(eq(productCategoriesSchema.productId, id));
        if (categories.length) {
          await tx.insert(productCategoriesSchema).values(
            categories.map((cat: any) => ({
              id: undefined,
              productId: id,
              categoryId: cat.categoryId,
              isPrimary: cat.isPrimary,
            })),
          );
        }
      }

      if (optionGroups) {
        const existingGroups = await tx
          .select()
          .from(productOptionGroupsSchema)
          .where(eq(productOptionGroupsSchema.productId, id));

        for (const group of existingGroups) {
          await tx
            .delete(productOptionsSchema)
            .where(eq(productOptionsSchema.optionGroupId, group.id));
        }

        await tx
          .delete(productOptionGroupsSchema)
          .where(eq(productOptionGroupsSchema.productId, id));

        if (optionGroups.length) {
          for (const group of optionGroups) {
            const [newGroup] = await tx
              .insert(productOptionGroupsSchema)
              .values({
                id: undefined,
                name: group.name,
                productId: id,
                displayOrder: group.displayOrder,
              })
              .returning();

            if (group.options?.length) {
              await tx.insert(productOptionsSchema).values(
                group.options.map((option: any) => ({
                  id: undefined,
                  optionGroupId: newGroup.id,
                  name: option.name,
                  additionalPrice: option.additionalPrice,
                  sku: option.sku,
                  stock: option.stock,
                  displayOrder: option.displayOrder,
                })),
              );
            }
          }
        }
      }

      if (images) {
        await tx
          .delete(productImagesSchema)
          .where(eq(productImagesSchema.productId, id));
        if (images.length) {
          await tx.insert(productImagesSchema).values(
            images.map((img: any) => ({
              id: undefined,
              productId: id,
              url: img.url,
              altText: img.altText,
              isPrimary: img.isPrimary,
              displayOrder: img.displayOrder,
              optionId: img.optionId,
            })),
          );
        }
      }

      if (tags?.length) {
        await tx.insert(productTagsSchema).values(
          tags.map((tagId: number) => ({
            id: undefined,
            productId: updatedProduct.id,
            tagId,
          })),
        );
      }

      return [updatedProduct];
    });

    return product;
  }

  async deleteProduct(id: number): Promise<void> {
    await this.drizzleService.db
      .delete(productsSchema)
      .where(eq(productsSchema.id, id));
  }

  private buildProductQueryConditions = (filters: GetProductsRequestDto) => {
    const conditions = [];

    if (filters.status) {
      conditions.push(eq(productsSchema.status, filters.status));
    }
    if (filters.minPrice) {
      conditions.push(
        gte(productPricesSchema.basePrice, String(filters.minPrice)),
      );
    }
    if (filters.maxPrice) {
      conditions.push(
        lte(productPricesSchema.basePrice, String(filters.maxPrice)),
      );
    }
    if (filters.category?.length) {
      conditions.push(
        sql`${productsSchema.id} IN (
          SELECT product_id 
          FROM ${productCategoriesSchema} 
          WHERE category_id IN (${filters.category})
        )`,
      );
    }
    if (filters.seller) {
      conditions.push(eq(productsSchema.sellerId, filters.seller));
    }
    if (filters.brand) {
      conditions.push(eq(productsSchema.brandId, filters.brand));
    }
    if (filters.search) {
      conditions.push(
        or(
          like(productsSchema.name, `%${filters.search}%`),
          like(productsSchema.shortDescription, `%${filters.search}%`),
        ),
      );
    }

    return conditions.length ? and(...conditions) : undefined;
  };

  private getProductListRelations() {
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

  private getProductDetailRelations() {
    return {
      seller: {
        columns: {
          id: true,
          name: true,
          logoUrl: true,
          rating: true,
          contactEmail: true,
          contactPhone: true,
        },
      },
      brand: {
        columns: {
          id: true,
          name: true,
          logoUrl: true,
          website: true,
        },
      },
      detail: {
        columns: {
          id: true,
          weight: true,
          dimensions: true,
          materials: true,
          countryOfOrigin: true,
          warrantyInfo: true,
          careInstructions: true,
          additionalInfo: true,
        },
      },
      price: {
        columns: {
          id: true,
          basePrice: true,
          salePrice: true,
          currency: true,
          taxRate: true,
        },
      },
      categories: {
        columns: {
          isPrimary: true,
        },
        with: {
          category: {
            columns: {
              id: true,
              name: true,
              slug: true,
            },
            with: {
              parent: {
                columns: {
                  id: true,
                  name: true,
                  slug: true,
                },
              },
            },
          },
        },
      },
      optionGroups: {
        with: {
          options: {
            columns: {
              id: true,
              name: true,
              stock: true,
              additionalPrice: true,
              sku: true,
              displayOrder: true,
            },
          },
        },
      },
      images: {
        columns: {
          id: true,
          url: true,
          altText: true,
          isPrimary: true,
          displayOrder: true,
          optionId: true,
        },
      },
      tags: {
        with: {
          tag: {
            columns: {
              id: true,
              name: true,
              slug: true,
            },
          },
        },
      },
      reviews: {
        with: {
          user: {
            columns: {
              id: true,
              name: true,
              avatarUrl: true,
            },
          },
        },
      },
    };
  }

  async createProductOption(
    dto: CreateProductOptionRequestDto,
  ): Promise<CreateProductOptionResponseData> {
    const [option] = await this.drizzleService.db
      .insert(productOptionsSchema)
      .values({
        id: undefined,
        optionGroupId: dto.optionGroupId,
        name: dto.name,
        additionalPrice: String(dto.additionalPrice),
        sku: dto.sku,
        stock: dto.stock,
        displayOrder: dto.displayOrder,
      })
      .returning({
        id: productOptionsSchema.id,
        optionGroupId: productOptionsSchema.optionGroupId,
        name: productOptionsSchema.name,
        additionalPrice: productOptionsSchema.additionalPrice,
        sku: productOptionsSchema.sku,
        stock: productOptionsSchema.stock,
        displayOrder: productOptionsSchema.displayOrder,
      });

    return {
      ...option,
      additionalPrice: Number(option.additionalPrice),
    };
  }

  async updateProductOption(
    optionId: number,
    dto: UpdateProductOptionRequestDto,
  ): Promise<UpdateProductOptionResponseData> {
    const [option] = await this.drizzleService.db
      .update(productOptionsSchema)
      .set({
        name: dto.name,
        additionalPrice: String(dto.additionalPrice),
        sku: dto.sku,
        stock: dto.stock,
        displayOrder: dto.displayOrder,
      })
      .where(and(eq(productOptionsSchema.id, optionId)))
      .returning({
        id: productOptionsSchema.id,
        optionGroupId: productOptionsSchema.optionGroupId,
        name: productOptionsSchema.name,
        additionalPrice: productOptionsSchema.additionalPrice,
        sku: productOptionsSchema.sku,
        stock: productOptionsSchema.stock,
        displayOrder: productOptionsSchema.displayOrder,
      });

    return {
      ...option,
      additionalPrice: Number(option.additionalPrice),
    };
  }

  async deleteProductOption(optionId: number): Promise<void> {
    await this.drizzleService.db
      .delete(productOptionsSchema)
      .where(eq(productOptionsSchema.id, optionId));
  }

  async createProductImage(
    productId: number,
    dto: CreateProductImageRequestDto,
  ): Promise<CreateProductImageResponseData> {
    const [image] = await this.drizzleService.db
      .insert(productImagesSchema)
      .values({
        id: undefined,
        productId,
        url: dto.url,
        altText: dto.altText,
        isPrimary: dto.isPrimary,
        displayOrder: dto.displayOrder,
        optionId: dto.optionId,
      })
      .returning({
        id: productImagesSchema.id,
        url: productImagesSchema.url,
        altText: productImagesSchema.altText,
        isPrimary: productImagesSchema.isPrimary,
        displayOrder: productImagesSchema.displayOrder,
        optionId: productImagesSchema.optionId,
      });

    return image;
  }
}
