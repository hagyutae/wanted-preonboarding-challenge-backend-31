import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";

import { type_orm_config } from "@libs/config/typeorm.config";
import ProductModule from "@product/module";
import CategoryModule from "@category/module";
import ReviewModule from "@review/module";
import BrowsingModule from "@browsing/module";

@Module({
  imports: [
    TypeOrmModule.forRootAsync(type_orm_config),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: [".env"],
    }),
    ProductModule,
    CategoryModule,
    ReviewModule,
    BrowsingModule,
  ],
})
export class AppModule {}
