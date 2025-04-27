import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";

import ProductService from "./application/ProductService";
import typeormConfigProvider from "./infrastructure/provider";
import {
  BrandEntity,
  CategoryEntity,
  ProductCategoryEntity,
  ProductDetailEntity,
  ProductEntity,
  ProductImageEntity,
  ProductOptionEntity,
  ProductOptionGroupEntity,
  ProductPriceEntity,
  ProductTagEntity,
  SellerEntity,
  TagEntity,
} from "./infrastructure/entities";
import { ProductController } from "./interfaces/controllers";

@Module({
  imports: [
    typeormConfigProvider,
    TypeOrmModule.forFeature([
      ProductEntity,
      ProductDetailEntity,
      ProductImageEntity,
      ProductOptionGroupEntity,
      ProductOptionEntity,
      ProductPriceEntity,
      ProductTagEntity,
      SellerEntity,
      BrandEntity,
      CategoryEntity,
      TagEntity,
      ProductCategoryEntity,
    ]),
  ],
  providers: [ProductService],
  controllers: [ProductController],
})
export class AppModule {}
