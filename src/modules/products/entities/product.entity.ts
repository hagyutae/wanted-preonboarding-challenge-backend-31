import { createSelectSchema } from 'drizzle-zod';
import { products } from '~/database/schema';

export const ProductSelectSchema = createSelectSchema(products);
