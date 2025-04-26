import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";

import Product from "./domain/Product";
import Product_Service from "./application/Product_Service";
import typeormConfigProvider from "./infrastructure/provider";
import ProductRepository from "./infrastructure/ProductRepository";
import ProductController from "./interfaces/ProductController";

@Module({
  imports: [typeormConfigProvider, TypeOrmModule.forFeature([Product])],
  providers: [
    {
      provide: "IRepository", // 인터페이스 제공
      useClass: ProductRepository, // 구현체 연결
    },
    Product_Service,
  ],
  controllers: [ProductController],
})
export class AppModule {}
