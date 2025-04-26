import { Module } from "@nestjs/common";

import typeormConfigProvider from "./infrastructure/provider";

@Module({
  imports: [typeormConfigProvider],
})
export class AppModule {}
