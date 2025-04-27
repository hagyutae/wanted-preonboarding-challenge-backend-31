import { Module } from "@nestjs/common";

import { ProductService, ProductOptionsService } from "./application";
import typeormConfigProvider from "./infrastructure/provider";
import { ProductController, ProductOptionsController } from "./interfaces/controllers";

@Module({
  imports: [typeormConfigProvider],
  providers: [ProductService, ProductOptionsService],
  controllers: [ProductController, ProductOptionsController],
})
export class AppModule {}
