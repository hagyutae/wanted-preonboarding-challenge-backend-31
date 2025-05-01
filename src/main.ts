import { BadRequestException, ValidationPipe } from "@nestjs/common";
import { NestFactory } from "@nestjs/core";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";
import { ValidationError } from "class-validator";

import { JwtInterceptor } from "./infrastructure/auth/jwtInterceptor";
import * as exception_filters from "./presentation/filters";
import { AppModule } from "./module";

async function bootstrap() {
  // 모듈 등록
  const app = await NestFactory.create(AppModule);

  // 유효성 검사 파이프라인
  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
      transformOptions: { enableImplicitConversion: true },
      exceptionFactory,
    }),
  );

  // 에러 핸들링
  for (const filter of Object.values(exception_filters)) {
    app.useGlobalFilters(new filter());
  }

  // JWT 인터셉터
  app.useGlobalInterceptors(new JwtInterceptor());

  // 스웨거 설정
  const swagger_config = new DocumentBuilder()
    .setTitle("API")
    .setDescription("API 명세서")
    .setVersion("1.0")
    .addBearerAuth()
    .build();

  const document = SwaggerModule.createDocument(app, swagger_config);

  SwaggerModule.setup("swagger-ui", app, document);

  // 앱 실행
  await app.listen(process.env.PORT ?? 3000);
}
void bootstrap();

// class-validator 유효성 검사 에러 핸들링
function exceptionFactory(errors: ValidationError[]) {
  const details: Record<string, string> = {};
  for (const error of errors) {
    const field = error.property;
    const messages = Object.values(error.constraints || {});
    if (messages.length > 0) {
      details[field] = messages[0];
    }
  }
  return new BadRequestException({
    message: "입력 데이터가 유효하지 않습니다.",
    details,
  });
}
