import { BadRequestException, ValidationPipe } from "@nestjs/common";
import { NestFactory, Reflector } from "@nestjs/core";
import { ValidationError } from "class-validator";

import generatorSwagger from "@utility/generatorSwagger";
import { JwtInterceptor } from "@product/infrastructure/auth/jwtInterceptor";
import * as exception_filters from "./libs/common/filters";
import { ResponseInterceptor } from "./libs/common/interceptors/ResponseInterceptor";
import { AppModule } from "./module";

async function bootstrap() {
  // 모듈 등록
  const app = await NestFactory.create(AppModule);

  // 유효성 검사 파이프라인
  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
      transformOptions: { enableImplicitConversion: true },
      forbidNonWhitelisted: true,
      exceptionFactory,
    }),
  );

  // 응답 인터셉터
  app.useGlobalInterceptors(new ResponseInterceptor(app.get(Reflector)));

  // 에러 핸들링
  for (const filter of Object.values(exception_filters)) {
    app.useGlobalFilters(new filter());
  }

  // JWT 인터셉터
  app.useGlobalInterceptors(new JwtInterceptor());

  // Swagger 설정
  generatorSwagger(app);

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
