import { Module } from "@nestjs/common";

import * as services from "./application/services";
import repository_providers from "./infrastructure/provider";
import * as controllers from "./presentation/controllers";

@Module({
  providers: [...Object.values(services), ...repository_providers],
  controllers: [...Object.values(controllers)],
})
export default class BrowsingModule {}
