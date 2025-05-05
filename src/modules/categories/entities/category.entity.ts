import { createSelectSchema } from 'drizzle-zod';
import { z } from 'zod';

import { categories } from '~/database/schema';

export const CategorySelectSchema = createSelectSchema(categories);
export const CategoryWithChildrenSchema = CategorySelectSchema.extend({
  children: z.array(z.lazy(() => CategoryWithChildrenSchema)).optional(),
});
