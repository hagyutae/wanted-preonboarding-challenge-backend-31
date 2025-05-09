import { Module } from "@nestjs/common";

import BrowsingModule from "@browsing/module";

import * as services from "./application/services";
import repository_providers from "./infrastructure/provider";
import * as controllers from "./presentation/controllers";

@Module({
  imports: [BrowsingModule],
  providers: [...Object.values(services), ...repository_providers],
  controllers: [...Object.values(controllers)],
  exports: ["IProductRepository"],
})
export default class ProductModule {}
