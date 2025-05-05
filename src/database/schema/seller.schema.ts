import {
  pgTable,
  bigint,
  varchar,
  text,
  timestamp,
  decimal,
} from 'drizzle-orm/pg-core';

export const sellers = pgTable('sellers', {
  id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
  name: varchar('name', { length: 100 }).notNull(),
  description: text('description'),
  logoUrl: varchar('logo_url', { length: 255 }),
  rating: decimal('rating', { precision: 3, scale: 2 }),
  contactEmail: varchar('contact_email', { length: 100 }),
  contactPhone: varchar('contact_phone', { length: 20 }),
  createdAt: timestamp('created_at').defaultNow(),
});
