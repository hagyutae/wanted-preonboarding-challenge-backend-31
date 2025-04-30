import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";

import {
  CategoryService,
  MainService,
  ProductOptionsService,
  ProductService,
  ReviewService,
} from "./application/services";
import typeormConfigProvider from "./infrastructure/provider";
import {
  CategoryController,
  MainController,
  ProductController,
  ProductOptionsController,
  ReviewController,
} from "./presentation/controllers";

@Module({
  imports: [
    TypeOrmModule.forRootAsync(typeormConfigProvider),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: [".env"],
    }),
  ],
  providers: [ProductService, ProductOptionsService, CategoryService, ReviewService, MainService],
  controllers: [
    ProductController,
    ProductOptionsController,
    CategoryController,
    ReviewController,
    MainController,
  ],
})
export class AppModule {}
