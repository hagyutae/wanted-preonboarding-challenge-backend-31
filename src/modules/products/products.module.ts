import { Module } from '@nestjs/common';
import { ProductsService } from './products.service';
import { ProductsController } from './products.controller';
import { DrizzleModule } from '~/database/drizzle.module';
import { ProductsRepository } from './products.repository';
import { ReviewsModule } from '../reviews/reviews.module';

@Module({
  imports: [DrizzleModule, ReviewsModule],
  controllers: [ProductsController],
  providers: [ProductsService, ProductsRepository],
})
export class ProductsModule {}
