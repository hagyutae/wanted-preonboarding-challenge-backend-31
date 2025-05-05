import { pgTable, bigint, varchar, text, integer, timestamp, boolean } from "drizzle-orm/pg-core";
import { relations } from "drizzle-orm";
import { products } from "./product.schema";
import { users } from "./user.schema";

export const reviews = pgTable('reviews', {
    id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
    productId: bigint('product_id', { mode: 'number' }).references(() => products.id, { onDelete: 'cascade' }),
    userId: bigint('user_id', { mode: 'number' }).references(() => users.id, { onDelete: 'set null' }),
    rating: integer('rating').notNull(),
    title: varchar('title', { length: 255 }),
    content: text('content'),
    createdAt: timestamp('created_at').defaultNow(),
    updatedAt: timestamp('updated_at').defaultNow(),
    verifiedPurchase: boolean('verified_purchase').default(false),
    helpfulVotes: integer('helpful_votes').default(0)
});

export const reviewsRelations = relations(reviews, ({ one }) => ({
    product: one(products, {
        fields: [reviews.productId],
        references: [products.id],
    }),
    user: one(users, {
        fields: [reviews.userId],
        references: [users.id],
    }),
})); 