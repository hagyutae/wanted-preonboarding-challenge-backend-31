import { pgTable, bigint, varchar, integer } from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { products } from './product.schema';
import { productOptions } from './product-option.schema';

export const productOptionGroups = pgTable('product_option_groups', {
  id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
  productId: bigint('product_id', { mode: 'number' }).references(
    () => products.id,
    { onDelete: 'cascade' },
  ),
  name: varchar('name', { length: 100 }).notNull(),
  displayOrder: integer('display_order').default(0),
});

export const productOptionGroupsRelations = relations(
  productOptionGroups,
  ({ one, many }) => ({
    product: one(products, {
      fields: [productOptionGroups.productId],
      references: [products.id],
    }),
    options: many(productOptions),
  }),
);
