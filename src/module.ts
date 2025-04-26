import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";

import Product_Service from "./application/Product_Service";
import typeormConfigProvider from "./infrastructure/provider";
import ProductRepository from "./infrastructure/ProductRepository";
import ProductController from "./interfaces/ProductController";
import { ProductEntity } from "./infrastructure/entities/Product.entity";

@Module({
  imports: [typeormConfigProvider, TypeOrmModule.forFeature([ProductEntity])],
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
