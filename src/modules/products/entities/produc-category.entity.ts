import { createSelectSchema } from 'drizzle-zod';
import { productCategories } from '~/database/schema';

export const ProductCategorySelectSchema =
  createSelectSchema(productCategories);
