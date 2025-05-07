import { Module } from '@nestjs/common';
import { CategoriesService } from './categories.service';
import { CategoriesController } from './categories.controller';
import { DrizzleModule } from '~/database/drizzle.module';
import { CategoryRepository } from './categories.repository';

@Module({
  imports: [DrizzleModule],
  controllers: [CategoriesController],
  providers: [CategoriesService, CategoryRepository],
})
export class CategoriesModule {}
