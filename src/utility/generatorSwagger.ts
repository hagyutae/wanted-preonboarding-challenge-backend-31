import { INestApplication } from "@nestjs/common";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";

export default function generatorSwagger(app: INestApplication<any>) {
  const swagger_config = new DocumentBuilder()
    .setTitle("API 명세서")
    .setDescription("CQRS 시스템 설계/구축 챌린지 - API 명세서")
    .setVersion("1.0")
    .addBearerAuth()
    .build();

  const document = SwaggerModule.createDocument(app, swagger_config);

  // 스웨거 UI 설정
  SwaggerModule.setup("swagger-ui", app, document, {
    /**
     * 제거 필요
     * 인증, 인가 미구현으로 자동 통과되도록 설정
     *
     **/
    swaggerOptions: {
      persistAuthorization: true,
      requestInterceptor: (req: { headers: { [x: string]: string } }) => {
        req.headers["Authorization"] = "Bearer Token";
        return req;
      },
    },
  });

  return document;
}
