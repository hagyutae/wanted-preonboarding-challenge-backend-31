import { Module } from "@nestjs/common";

import { ProductService, ProductOptionsService, CategoryService } from "./application";
import typeormConfigProvider from "./infrastructure/provider";
import {
  CategoryController,
  ProductController,
  ProductOptionsController,
} from "./interfaces/controllers";

@Module({
  imports: [typeormConfigProvider],
  providers: [ProductService, ProductOptionsService, CategoryService],
  controllers: [ProductController, ProductOptionsController, CategoryController],
})
export class AppModule {}
