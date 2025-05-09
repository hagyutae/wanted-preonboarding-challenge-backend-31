import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";

import * as services from "./application/services";
import { repository_providers, type_orm_config } from "./infrastructure/provider";
import * as controllers from "./presentation/controllers";

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
