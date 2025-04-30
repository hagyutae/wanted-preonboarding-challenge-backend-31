import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";

import { ProductService, ProductOptionsService, CategoryService, MainService } from "./application";
import typeormConfigProvider from "./infrastructure/provider";
import {
  ProductController,
  ProductOptionsController,
  ReviewController,
  CategoryController,
  MainController,
} from "./interfaces/controllers";
import ReviewService from "./application/ReviewService";
import { TypeOrmModule } from "@nestjs/typeorm";

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
