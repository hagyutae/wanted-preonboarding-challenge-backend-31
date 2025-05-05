import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { HttpExceptionFilter } from './common/filters/http-exception.filter';
import { TransformInterceptor } from './common/interceptors/transform.interceptor';
import { ValidationPipe } from '@nestjs/common';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger'; // 추가
import { I18nService } from 'nestjs-i18n';

async function bootstrap() {
  try {
    const app = await NestFactory.create(AppModule);

    app.useGlobalPipes(new ValidationPipe({ transform: true }));
    app.useGlobalFilters(new HttpExceptionFilter());
    app.useGlobalInterceptors(new TransformInterceptor(app.get(I18nService)));

    // Swagger 설정 시작
    const config = new DocumentBuilder()
      .setTitle('Products API')
      .setDescription('상품 API 문서입니다.')
      .setVersion('1.0')
      .build();
    const document = SwaggerModule.createDocument(app, config);
    SwaggerModule.setup('api-docs', app, document);
    await app.listen(process.env.PORT || 3000);
  } catch (err) {
    console.error('🔴 서버 실행이 실패했습니다.', err);
    process.exit(1);
  }
}
bootstrap();
