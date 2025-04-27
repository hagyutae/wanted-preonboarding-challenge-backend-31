import { Module } from "@nestjs/common";

import ProductService from "./application/ProductService";
import typeormConfigProvider from "./infrastructure/provider";
import { ProductController } from "./interfaces/controllers";

@Module({
  imports: [typeormConfigProvider],
  providers: [ProductService],
  controllers: [ProductController],
})
export class AppModule {}
