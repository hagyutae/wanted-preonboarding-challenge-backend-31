import { Module } from "@nestjs/common";

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

@Module({
  imports: [typeormConfigProvider],
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
