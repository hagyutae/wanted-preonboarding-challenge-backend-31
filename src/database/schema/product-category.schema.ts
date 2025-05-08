import { pgTable, bigint, boolean } from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { products } from './product.schema';
import { categories } from './category.schema';

export const productCategories = pgTable('product_categories', {
  id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
  productId: bigint('product_id', { mode: 'number' }).references(
    () => products.id,
    { onDelete: 'cascade' },
  ),
  categoryId: bigint('category_id', { mode: 'number' }).references(
    () => categories.id,
    { onDelete: 'cascade' },
  ),
  isPrimary: boolean('is_primary').default(false),
});

export const productCategoriesRelations = relations(
  productCategories,
  ({ one }) => ({
    product: one(products, {
      fields: [productCategories.productId],
      references: [products.id],
    }),
    category: one(categories, {
      fields: [productCategories.categoryId],
      references: [categories.id],
    }),
  }),
);
