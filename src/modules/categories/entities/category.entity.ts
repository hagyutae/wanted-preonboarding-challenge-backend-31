import { z } from 'zod';

// 기본 카테고리 스키마
export const CategorySchema = z.object({
  id: z.number(),
  name: z.string(),
  slug: z.string(),
  description: z.string().nullable(),
  parentId: z.number().nullable(),
  level: z.number(),
  imageUrl: z.string().nullable(),
});

// 관계를 포함한 카테고리 스키마
export const CategoryWithRelationsSchema = CategorySchema.extend({
  parent: z.lazy(() => CategorySchema).optional(),
  children: z.array(z.lazy(() => CategoryWithRelationsSchema)).optional(),
});

// 타입 추론
export type Category = z.infer<typeof CategorySchema>;
export type CategoryWithRelations = z.infer<typeof CategoryWithRelationsSchema>;
