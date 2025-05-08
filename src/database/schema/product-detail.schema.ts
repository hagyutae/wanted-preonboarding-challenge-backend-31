import {
  pgTable,
  bigint,
  varchar,
  text,
  decimal,
  jsonb,
} from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { products } from './product.schema';

export const productDetails = pgTable('product_details', {
  id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
  productId: bigint('product_id', { mode: 'number' }).references(
    () => products.id,
    { onDelete: 'cascade' },
  ),
  weight: decimal('weight', { precision: 10, scale: 2 }),
  dimensions: jsonb('dimensions'),
  materials: text('materials'),
  countryOfOrigin: varchar('country_of_origin', { length: 100 }),
  warrantyInfo: text('warranty_info'),
  careInstructions: text('care_instructions'),
  additionalInfo: jsonb('additional_info'),
});

export const productDetailsRelations = relations(productDetails, ({ one }) => ({
  product: one(products, {
    fields: [productDetails.productId],
    references: [products.id],
  }),
}));
