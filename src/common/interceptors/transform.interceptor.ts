import {
  Injectable,
  NestInterceptor,
  ExecutionContext,
  CallHandler,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SuccessResponse } from '../types/response.types';
import { I18nService } from 'nestjs-i18n';

@Injectable()
export class TransformInterceptor<T>
  implements NestInterceptor<T, SuccessResponse<T>>
{
  constructor(private readonly i18n: I18nService) {}

  intercept(
    context: ExecutionContext,
    next: CallHandler,
  ): Observable<SuccessResponse<T>> {
    const request = context.switchToHttp().getRequest();
    const lang = request.headers['accept-language'] || 'ko';

    return next.handle().pipe(
      map((data) => ({
        success: true,
        data,
        message: this.i18n.t('response.success', { lang }),
      })),
    );
  }
}
