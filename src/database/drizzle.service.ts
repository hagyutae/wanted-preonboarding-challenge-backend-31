import { Inject, Injectable } from '@nestjs/common';
import { NodePgDatabase } from 'drizzle-orm/node-postgres';

import { DrizzleAsyncProvider } from './drizzle.provider';
import * as schema from './schema';

@Injectable()
export class DrizzleService {
  constructor(
    @Inject(DrizzleAsyncProvider)
    private readonly _db: NodePgDatabase<typeof schema>,
  ) {}

  get db(): NodePgDatabase<typeof schema> {
    return this._db;
  }
}
