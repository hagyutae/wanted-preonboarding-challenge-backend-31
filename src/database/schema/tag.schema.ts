import { pgTable, bigint, varchar } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { productTags } from "./product-tag.schema";

export const tags = pgTable('tags', {
    id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
    name: varchar('name', { length: 100 }).notNull(),
    slug: varchar('slug', { length: 100 }).notNull().unique()
});

export const tagsRelations = relations(tags, ({ many }) => ({
    productTags: many(productTags),
})); 