import { pgTable, bigint } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { products } from "./product.schema";
import { tags } from "./tag.schema";

export const productTags = pgTable('product_tags', {
    id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
    productId: bigint('product_id', { mode: 'number' }).references(() => products.id, { onDelete: 'cascade' }),
    tagId: bigint('tag_id', { mode: 'number' }).references(() => tags.id, { onDelete: 'cascade' })
});

export const productTagsRelations = relations(productTags, ({ one }) => ({
    product: one(products, {
        fields: [productTags.productId],
        references: [products.id],
    }),
    tag: one(tags, {
        fields: [productTags.tagId],
        references: [tags.id],
    }),
})); 