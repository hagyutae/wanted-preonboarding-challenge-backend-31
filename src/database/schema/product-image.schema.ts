import {
  pgTable,
  bigint,
  varchar,
  boolean,
  integer,
} from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { products } from './product.schema';
import { productOptions } from './product-option.schema';

export const productImages = pgTable('product_images', {
  id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
  productId: bigint('product_id', { mode: 'number' }).references(
    () => products.id,
    { onDelete: 'cascade' },
  ),
  url: varchar('url', { length: 255 }).notNull(),
  altText: varchar('alt_text', { length: 255 }),
  isPrimary: boolean('is_primary').default(false),
  displayOrder: integer('display_order').default(0),
  optionId: bigint('option_id', { mode: 'number' }).references(
    () => productOptions.id,
    { onDelete: 'set null' },
  ),
});

export const productImagesRelations = relations(productImages, ({ one }) => ({
  product: one(products, {
    fields: [productImages.productId],
    references: [products.id],
  }),
  option: one(productOptions, {
    fields: [productImages.optionId],
    references: [productOptions.id],
  }),
}));
