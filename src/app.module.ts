import { Module } from '@nestjs/common';
import { ProductsModule } from '~/modules/products/products.module';
import { ReviewsModule } from '~/modules/reviews/reviews.module';
import { CategoriesModule } from '~/modules/categories/categories.module';

@Module({
  imports: [ProductsModule, ReviewsModule, CategoriesModule],
  controllers: [],
  providers: [],
})
export class AppModule {}
