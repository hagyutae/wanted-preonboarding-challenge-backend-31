import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { z } from 'zod';

import { ProductsModule } from '~/modules/products/products.module';
import { ReviewsModule } from '~/modules/reviews/reviews.module';
import { CategoriesModule } from '~/modules/categories/categories.module';
import { DrizzleModule } from '~/database/drizzle.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: '.env',
      validationSchema: z.object({
        DATABASE_URL: z.string(),
      }),
    }),
    ProductsModule,
    ReviewsModule,
    CategoriesModule,
    DrizzleModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
