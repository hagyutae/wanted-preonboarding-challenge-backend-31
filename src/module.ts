import { Module } from "@nestjs/common";
import { ConfigModule } from "@nestjs/config";
import { TypeOrmModule } from "@nestjs/typeorm";

import * as services from "./application/services";
import typeormConfigProvider from "./infrastructure/provider";
import * as repositories from "./infrastructure/repositories";
import * as controllers from "./presentation/controllers";

@Module({
  imports: [
    TypeOrmModule.forRootAsync(typeormConfigProvider),
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: [".env"],
    }),
  ],
  providers: [...Object.values(services), ...Object.values(repositories)],
  controllers: [...Object.values(controllers)],
})
export class AppModule {}
