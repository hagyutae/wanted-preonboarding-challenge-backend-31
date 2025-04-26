import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";

import ProductService from "./application/ProductService";
import typeormConfigProvider from "./infrastructure/provider";
import ProductRepository from "./infrastructure/ProductRepository";
import { ProductEntity } from "./infrastructure/entities";
import { ProductController, ProductOptionsController } from "./interfaces/controllers";

@Module({
  imports: [typeormConfigProvider, TypeOrmModule.forFeature([ProductEntity])],
  providers: [
    {
      provide: "IRepository", // 인터페이스 제공
      useClass: ProductRepository, // 구현체 연결
    },
    ProductService,
  ],
  controllers: [ProductController, ProductOptionsController],
})
export class AppModule {}
