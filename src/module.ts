import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";

import { type_orm_config } from "./libs/config/typeorm.config";
import ProductModule from "./product/module";

@Module({
  imports: [
    TypeOrmModule.forRootAsync(type_orm_config),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: [".env"],
    }),
    ProductModule,
  ],
})
export class AppModule {}
