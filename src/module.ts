import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";

import * as services from "./product/application/services";
import { repository_providers, type_orm_config } from "./product/infrastructure/provider";
import * as controllers from "./product/presentation/controllers";

@Module({
  imports: [
    TypeOrmModule.forRootAsync(type_orm_config),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: [".env"],
    }),
  ],
  providers: [...Object.values(services), ...repository_providers],
  controllers: [...Object.values(controllers)],
})
export class AppModule {}
