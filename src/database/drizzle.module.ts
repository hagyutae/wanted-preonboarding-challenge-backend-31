import { Module } from '@nestjs/common';
import { DrizzleAsyncProvider, drizzleProvider } from './drizzle.provider';
import { DrizzleService } from './drizzle.service';

@Module({
  providers: [...drizzleProvider, DrizzleService],
  exports: [DrizzleAsyncProvider, DrizzleService],
})
export class DrizzleModule {}
