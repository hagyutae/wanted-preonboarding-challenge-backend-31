import { promises } from "fs";

import { NestFactory } from "@nestjs/core";
import { EntityManager } from "typeorm";
import * as YAML from "yamljs";

import { Module } from "@nestjs/common";
import { repository_providers } from "src/infrastructure/provider";
import * as services from "../application/services";
import * as controllers from "../presentation/controllers";
import generatorSwagger from "./generatorSwagger";

@Module({
  providers: [
    {
      provide: EntityManager,
      useValue: {},
    },
    ...Object.values(services),
    ...repository_providers,
  ],
  controllers: [...Object.values(controllers)],
})
class SwaggerAppModule {}

export async function openapi() {
  const app = await NestFactory.create(SwaggerAppModule);

  const document = generatorSwagger(app);

  const yamlDocument = YAML.stringify(document, 10, 2);

  try {
    await promises.mkdir("./dist", { recursive: true });
  } catch (err) {
    console.error("Error creating directory", err);
    process.exit(1);
  }

  try {
    await promises.writeFile("./dist/openapi.yaml", String(yamlDocument));
  } catch (err) {
    console.error("Error writing file", err);
    process.exit(1);
  }

  console.log("Swagger spec saved to openapi.yaml");
  process.exit(0);
}

if (require.main === module) {
  void openapi();
}
