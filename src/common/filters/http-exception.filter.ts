// src/common/filters/http-exception.filter.ts
import {
  ExceptionFilter,
  Catch,
  ArgumentsHost,
  HttpException,
  HttpStatus,
  Logger,
} from '@nestjs/common';
import { Request, Response } from 'express';
import { ApiException } from '../exceptions/api.exception';
import { createErrorResponse } from '../utils/response.util';
import { ErrorCode } from '../types/response.type';

/**
 * 모든 HTTP 예외를 처리하는 전역 필터
 * 표준화된 에러 응답 형식으로 변환
 */
@Catch(HttpException)
export class HttpExceptionFilter implements ExceptionFilter {
  private readonly logger = new Logger(HttpExceptionFilter.name);

  catch(exception: unknown, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();
    const request = ctx.getRequest<Request>();

    // 기본 HTTP 상태 및 에러 정보 설정
    let status = HttpStatus.INTERNAL_SERVER_ERROR;
    let errorCode = ErrorCode.INTERNAL_ERROR;
    let message = '서버 내부 오류가 발생했습니다.';
    let details: Record<string, any> | undefined;

    // ApiException 처리
    if (exception instanceof ApiException) {
      status = exception.getStatus();
      errorCode = exception.errorCode as ErrorCode;
      message = exception.message;
      details = exception.details;
    }
    // NestJS HttpException 처리
    else if (exception instanceof HttpException) {
      status = exception.getStatus();

      // HTTP 상태 코드에 따른 에러 코드 매핑
      switch (status) {
        case HttpStatus.BAD_REQUEST:
          errorCode = ErrorCode.INVALID_INPUT;
          break;
        case HttpStatus.UNAUTHORIZED:
          errorCode = ErrorCode.UNAUTHORIZED;
          break;
        case HttpStatus.FORBIDDEN:
          errorCode = ErrorCode.FORBIDDEN;
          break;
        case HttpStatus.NOT_FOUND:
          errorCode = ErrorCode.RESOURCE_NOT_FOUND;
          break;
        case HttpStatus.CONFLICT:
          errorCode = ErrorCode.CONFLICT;
          break;
        default:
          errorCode = ErrorCode.INTERNAL_ERROR;
      }

      const exceptionResponse = exception.getResponse();

      // 응답이 객체인 경우 메시지 추출
      if (typeof exceptionResponse === 'object' && exceptionResponse !== null) {
        message = (exceptionResponse as any).message || exception.message;

        // 클래스 벨리데이터 예외 등에서 상세 정보 추출
        if (Array.isArray((exceptionResponse as any).message)) {
          details = { violations: (exceptionResponse as any).message };
        }
      } else {
        message = exception.message;
      }
    }
    // 그 외 모든 예외를 내부 서버 오류로 처리
    else if (exception instanceof Error) {
      message = exception.message;
      this.logger.error(
        `Unhandled exception: ${exception.message}`,
        exception.stack,
      );
    } else {
      this.logger.error('Unknown exception', exception);
    }

    // 요청 로그
    this.logger.error(
      `${request.method} ${request.url} - ${status}: ${message}`,
      request.body,
    );

    // 표준화된 에러 응답 반환
    const errorResponse = createErrorResponse(errorCode, message, details);
    response.status(status).json(errorResponse);
  }
}
