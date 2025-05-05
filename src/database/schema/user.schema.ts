import { pgTable, bigint, varchar, timestamp } from 'drizzle-orm/pg-core';
import { relations } from 'drizzle-orm';
import { reviews } from './review.schema';

export const users = pgTable('users', {
  id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
  name: varchar('name', { length: 100 }).notNull(),
  email: varchar('email', { length: 100 }).notNull().unique(),
  avatarUrl: varchar('avatar_url', { length: 255 }),
  createdAt: timestamp('created_at').defaultNow(),
});

export const usersRelations = relations(users, ({ many }) => ({
  reviews: many(reviews),
}));
