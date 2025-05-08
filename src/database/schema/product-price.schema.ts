import { pgTable, bigint, varchar, decimal } from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { products } from './product.schema';

export const productPrices = pgTable('product_prices', {
  id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
  productId: bigint('product_id', { mode: 'number' }).references(
    () => products.id,
    { onDelete: 'cascade' },
  ),
  basePrice: decimal('base_price', { precision: 12, scale: 2 }).notNull(),
  salePrice: decimal('sale_price', { precision: 12, scale: 2 }),
  costPrice: decimal('cost_price', { precision: 12, scale: 2 }),
  currency: varchar('currency', { length: 3 }).default('KRW'),
  taxRate: decimal('tax_rate', { precision: 5, scale: 2 }),
});

export const productPricesRelations = relations(productPrices, ({ one }) => ({
  product: one(products, {
    fields: [productPrices.productId],
    references: [products.id],
  }),
}));
