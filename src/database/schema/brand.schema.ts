import { pgTable, bigint, varchar, text } from "drizzle-orm/pg-core";

export const brands = pgTable('brands', {
    id: bigint('id', { mode: 'number' }).primaryKey().notNull(),
    name: varchar('name', { length: 100 }).notNull(),
    slug: varchar('slug', { length: 100 }).notNull().unique(),
    description: text('description'),
    logoUrl: varchar('logo_url', { length: 255 }),
    website: varchar('website', { length: 255 })
});