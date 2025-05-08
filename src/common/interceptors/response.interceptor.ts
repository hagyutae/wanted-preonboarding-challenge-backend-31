import {
  Injectable,
  NestInterceptor,
  ExecutionContext,
  CallHandler,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { createSuccessResponse } from '../utils/response.util';
import { ApiSuccessResponse } from '../types/response.type';

/**
 * 응답 포맷 인터셉터
 * 컨트롤러에서 반환되는 데이터를 표준 응답 형식으로 변환
 */
@Injectable()
export class ResponseInterceptor<T>
  implements NestInterceptor<T, ApiSuccessResponse<T>>
{
  /**
   * 이미 표준화된 응답인지 확인
   */
  private isStandardResponse(data: any): boolean {
    return (
      data &&
      typeof data === 'object' &&
      'success' in data &&
      'data' in data &&
      'message' in data
    );
  }

  intercept(
    _context: ExecutionContext,
    next: CallHandler,
  ): Observable<ApiSuccessResponse<T>> {
    return next.handle().pipe(
      map((data) => {
        // 이미 표준화된 응답이면 그대로 반환
        if (this.isStandardResponse(data)) {
          return data;
        }

        // 데이터를 표준 성공 응답 형식으로 변환
        return createSuccessResponse(data);
      }),
    );
  }
}
