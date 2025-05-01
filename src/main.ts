import { ValidationPipe } from "@nestjs/common";
import { NestFactory } from "@nestjs/core";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";

import { AppModule } from "./module";
import * as exception_filters from "./presentation/filters";

async function bootstrap() {
  // 모듈 등록
  const app = await NestFactory.create(AppModule);

  // 유효성 검사 파이프라인
  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
      transformOptions: { enableImplicitConversion: true },
    }),
  );

  // 에러 핸들링
  for (const filter of Object.values(exception_filters)) {
    app.useGlobalFilters(new filter());
  }

  // 스웨거 설정
  const swagger_config = new DocumentBuilder()
    .setTitle("API")
    .setDescription("API 명세서")
    .setVersion("1.0")
    .build();

  const document = SwaggerModule.createDocument(app, swagger_config);

  SwaggerModule.setup("swagger-ui", app, document);

  // 앱 실행
  await app.listen(process.env.PORT ?? 3000);
}
void bootstrap();
