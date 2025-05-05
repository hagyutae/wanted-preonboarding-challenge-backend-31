import { defineConfig } from 'drizzle-kit';

export default defineConfig({
    schema: './src/database/schema/*.ts',
    out: './src/database/migrations',
    dbCredentials: {
        url: process.env.DATABASE_URL || '',
    },
    dialect: 'postgresql'
})