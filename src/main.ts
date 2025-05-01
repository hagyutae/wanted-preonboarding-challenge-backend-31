import { ValidationPipe } from "@nestjs/common";
import { NestFactory } from "@nestjs/core";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";

import { AppModule } from "./module";

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
      transformOptions: { enableImplicitConversion: true },
    }),
  );

  const swagger_config = new DocumentBuilder()
    .setTitle("API")
    .setDescription("API 명세서")
    .setVersion("1.0")
    .build();

  const document = SwaggerModule.createDocument(app, swagger_config);

  SwaggerModule.setup("swagger-ui", app, document);

  await app.listen(process.env.PORT ?? 3000);
}
void bootstrap();
