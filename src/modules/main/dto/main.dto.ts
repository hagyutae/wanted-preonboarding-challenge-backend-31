import { z } from 'zod';
import { ProductResponseSchema } from '~/modules/products/entities/product.entity';
import { CategorySchema } from '~/modules/categories/entities/category.entity';
import { createSuccessResponseSchema } from '~/common/utils/response-schema.util';

export const MainPageProductSchema = ProductResponseSchema.pick({
  id: true,
  name: true,
  slug: true,
  shortDescription: true,
  basePrice: true,
  salePrice: true,
  currency: true,
  primaryImage: true,
  brand: true,
  seller: true,
  rating: true,
  reviewCount: true,
  inStock: true,
  status: true,
  createdAt: true,
});

export const FeaturedCategorySchema = CategorySchema.pick({
  id: true,
  name: true,
  slug: true,
  imageUrl: true,
}).extend({
  productCount: z.number(),
});

export const MainPageResponseSchema = z.object({
  newProducts: z.array(MainPageProductSchema),
  popularProducts: z.array(MainPageProductSchema),
  featuredCategories: z.array(FeaturedCategorySchema),
});

export type MainPageResponse = z.infer<typeof MainPageResponseSchema>;

export const GetMainPageResponseDtoSchema = createSuccessResponseSchema(
  MainPageResponseSchema,
);
export type GetMainPageResponseDto = z.infer<
  typeof GetMainPageResponseDtoSchema
>;
