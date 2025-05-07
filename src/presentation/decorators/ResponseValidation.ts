import { CallHandler, ExecutionContext, Injectable, NestInterceptor } from "@nestjs/common";
import { plainToInstance } from "class-transformer";
import { Observable, map } from "rxjs";

@Injectable()
export default class ResponseValidation<T extends object> implements NestInterceptor {
  constructor(private readonly dtoClass: new () => T) {}

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    return next.handle().pipe(
      map((data) => {
        const transform = (item: any) =>
          plainToInstance(this.dtoClass, item, { enableImplicitConversion: true });

        return Array.isArray(data) ? data.map(transform) : transform(data);
      }),
    );
  }
}
