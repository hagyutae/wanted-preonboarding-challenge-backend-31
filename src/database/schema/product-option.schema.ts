import { pgTable, bigint, varchar, decimal, integer } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { productOptionGroups } from "./product-option-group.schema";
import { productImages } from "./product-image.schema";

export const productOptions = pgTable('product_options', {
    id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
    optionGroupId: bigint('option_group_id', { mode: 'number' }).references(() => productOptionGroups.id, { onDelete: 'cascade' }),
    name: varchar('name', { length: 100 }).notNull(),
    additionalPrice: decimal('additional_price', { precision: 12, scale: 2 }).default('0'),
    sku: varchar('sku', { length: 100 }),
    stock: integer('stock').default(0),
    displayOrder: integer('display_order').default(0)
});

export const productOptionsRelations = relations(productOptions, ({ one, many }) => ({
    optionGroup: one(productOptionGroups, {
        fields: [productOptions.optionGroupId],
        references: [productOptionGroups.id],
    }),
    images: many(productImages),
})); 