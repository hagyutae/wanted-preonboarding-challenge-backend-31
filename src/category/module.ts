import { Module } from "@nestjs/common";

import { ProductRepository } from "@product/infrastructure/repositories";
import * as services from "./application/services";
import repository_providers from "./infrastructure/provider";
import * as controllers from "./presentation/controllers";

@Module({
  providers: [
    ...Object.values(services),
    ...repository_providers,
    { provide: "IProductRepository", useClass: ProductRepository },
  ],
  controllers: [...Object.values(controllers)],
})
export default class CategoryModule {}
