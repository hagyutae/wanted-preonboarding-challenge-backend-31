import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";

import * as services from "./application/services";
import typeormConfigProvider from "./infrastructure/provider";
import {
  ProductRepository,
  ProductOptionsRepository,
  ProductImageRepository,
  ReviewRepository,
  CategoryRepository,
  MainRepository,
  ProductDetailRepository,
  ProductPriceRepository,
  ProductCategoryRepository,
  ProductOptionGroupRepository,
  ProductTagRepository,
} from "./infrastructure/repositories";
import * as controllers from "./presentation/controllers";

@Module({
  imports: [
    TypeOrmModule.forRootAsync(typeormConfigProvider),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: [".env"],
    }),
  ],
  providers: [
    ...Object.values(services),
    { provide: "IProductRepository", useClass: ProductRepository },
    { provide: "IProductOptionsRepository", useClass: ProductOptionsRepository },
    { provide: "IProductImageRepository", useClass: ProductImageRepository },
    { provide: "IReviewRepository", useClass: ReviewRepository },
    { provide: "ICategoryRepository", useClass: CategoryRepository },
    { provide: "IMainRepository", useClass: MainRepository },
    { provide: "IProductDetailRepository", useClass: ProductDetailRepository },
    { provide: "IProductPriceRepository", useClass: ProductPriceRepository },
    { provide: "IProductCategoryRepository", useClass: ProductCategoryRepository },
    { provide: "IProductOptionGroupRepository", useClass: ProductOptionGroupRepository },
    { provide: "IProductTagRepository", useClass: ProductTagRepository },
  ],
  controllers: [...Object.values(controllers)],
})
export class AppModule {}
