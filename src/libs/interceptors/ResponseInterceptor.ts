/* eslint-disable @typescript-eslint/no-unsafe-argument */
import { CallHandler, ExecutionContext, Injectable, NestInterceptor } from "@nestjs/common";
import { Reflector } from "@nestjs/core";
import { plainToInstance } from "class-transformer";
import { Observable, map } from "rxjs";

export const RESPONSE_DTO_KEY = "RESPONSE_DTO_KEY";

@Injectable()
export default class ResponseInterceptor implements NestInterceptor {
  constructor(private readonly reflector: Reflector) {}

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    const dto_class = this.reflector.get(RESPONSE_DTO_KEY, context.getHandler());

    if (!dto_class) return next.handle();

    return next.handle().pipe(
      map((data) => {
        const transform = (item: any) =>
          plainToInstance(dto_class, item, { enableImplicitConversion: true });

        return Array.isArray(data) ? data.map(transform) : transform(data);
      }),
    );
  }
}
