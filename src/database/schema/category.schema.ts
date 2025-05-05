import { pgTable, bigint, varchar, text, integer } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { productCategories } from "./product-category.schema";

export const categories = pgTable('categories', {
    id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
    name: varchar('name', { length: 100 }).notNull(),
    slug: varchar('slug', { length: 100 }).notNull().unique(),
    description: text('description'),
    parentId: bigint('parent_id', { mode: 'number' }).references(() => categories.id),
    level: integer('level').notNull(),
    imageUrl: varchar('image_url', { length: 255 })
});

export const categoriesRelations = relations(categories, ({ one, many }) => ({
    parent: one(categories, {
        fields: [categories.parentId],
        references: [categories.id],
    }),
    children: many(categories, {
        relationName: 'parent',
    }),
    productCategories: many(productCategories),
}));
