import { z } from 'zod';
import { createSuccessResponseSchema } from '~/common/utils/response-schema.util';
import {
  ProductSchema,
  ProductImageSchema,
  BrandSchema,
  SellerSchema,
} from '~/modules/products/entities/product.entity';
import { CategorySchema } from '~/modules/categories/entities/category.entity';

export const MainPageProductSchema = ProductSchema.pick({
  id: true,
  name: true,
  slug: true,
  shortDescription: true,
  status: true,
  createdAt: true,
}).extend({
  basePrice: z.number(),
  salePrice: z.number().nullable(),
  currency: z.string(),
  primaryImage: ProductImageSchema.pick({
    url: true,
    altText: true,
  }).nullable(),
  brand: BrandSchema.pick({
    id: true,
    name: true,
    logoUrl: true,
  }).nullable(),
  seller: SellerSchema.pick({
    id: true,
    name: true,
    logoUrl: true,
  }).nullable(),
  rating: z.number().nullable(),
  reviewCount: z.number(),
  inStock: z.boolean(),
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
