import { pgTable, bigint, varchar, text, timestamp } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { sellers } from "./seller.schema";
import { brands } from "./brand.schema";
import { productDetails } from "./product-detail.schema";
import { productPrices } from "./product-price.schema";
import { productCategories } from "./product-category.schema";
import { productOptionGroups } from "./product-option-group.schema";
import { productImages } from "./product-image.schema";
import { productTags } from "./product-tag.schema";
import { reviews } from "./review.schema";

export const products = pgTable('products', {
    id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
    name: varchar('name', { length: 255 }).notNull(),
    slug: varchar('slug', { length: 255 }).notNull().unique(),
    shortDescription: varchar('short_description', { length: 500 }),
    fullDescription: text('full_description'),
    createdAt: timestamp('created_at').defaultNow(),
    updatedAt: timestamp('updated_at').defaultNow(),
    sellerId: bigint('seller_id', { mode: 'number' }).references(() => sellers.id),
    brandId: bigint('brand_id', { mode: 'number' }).references(() => brands.id),
    status: varchar('status', { length: 20 }).notNull(),
});

export const productsRelations = relations(products, ({ one, many }) => ({
    seller: one(sellers, {
        fields: [products.sellerId],
        references: [sellers.id],
    }),
    brand: one(brands, {
        fields: [products.brandId],
        references: [brands.id],
    }),
    details: one(productDetails),
    prices: one(productPrices),
    categories: many(productCategories),
    optionGroups: many(productOptionGroups),
    images: many(productImages),
    tags: many(productTags),
    reviews: many(reviews),
}));
