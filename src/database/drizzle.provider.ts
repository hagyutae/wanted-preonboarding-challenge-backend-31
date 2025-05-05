import { ConfigService } from '@nestjs/config';
import { drizzle } from 'drizzle-orm/node-postgres';
import { Pool } from 'pg';

import * as schema from './schema';
import { NodePgDatabase } from 'drizzle-orm/node-postgres';

export const DrizzleAsyncProvider = Symbol('DrizzleAsyncProvider');

export const drizzleProvider = [
  {
    provide: DrizzleAsyncProvider,
    inject: [ConfigService],
    useFactory: async (configService: ConfigService) => {
      const connectionString = configService.get<string>('DATABASE_URL');
      const pool = new Pool({ connectionString });
      return drizzle(pool, { schema }) as unknown as NodePgDatabase<
        typeof schema
      >;
    },
  },
];
