import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { z } from 'zod';

import { ProductsModule } from '~/modules/products/products.module';
import { ReviewsModule } from '~/modules/reviews/reviews.module';
import { CategoriesModule } from '~/modules/categories/categories.module';
import { DrizzleModule } from '~/database/drizzle.module';

const envSchema = z.object({
  DATABASE_URL: z.string(),
});

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: '.env',
      validate: (config) => {
        const result = envSchema.safeParse(config);
        if (!result.success) {
          throw new Error('환경 변수 검증에 실패했습니다.');
        }
        return result.data;
      },
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
