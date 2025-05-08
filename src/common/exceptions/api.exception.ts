// src/common/exceptions/api.exception.ts
import { HttpException, HttpStatus } from '@nestjs/common';
import { ErrorCode } from '../types/response.type';

/**
 * API 예외 클래스
 * 표준 에러 포맷을 준수하는 커스텀 예외
 */
export class ApiException extends HttpException {
  readonly errorCode: ErrorCode;
  readonly details?: Record<string, any>;

  /**
   * @param code 에러 코드
   * @param message 에러 메시지
   * @param details 상세 에러 정보
   * @param status 에러 상태 코드
   */
  constructor(
    code: ErrorCode,
    message: string,
    details?: Record<string, any>,
    status?: HttpStatus,
  ) {
    const error = {
      code,
      message,
      ...(details && { details }),
    };

    super(
      {
        success: false,
        error,
      },
      status || HttpStatus.BAD_REQUEST,
    );

    this.errorCode = code;
    this.details = details;
  }
}

export class InvalidInputException extends ApiException {
  constructor(details?: Record<string, any>) {
    super(
      ErrorCode.INVALID_INPUT,
      '입력 데이터가 유효하지 않습니다.',
      details,
      HttpStatus.BAD_REQUEST,
    );
  }
}

export class ResourceNotFoundException extends ApiException {
  constructor(details?: Record<string, any>) {
    super(
      ErrorCode.RESOURCE_NOT_FOUND,
      '요청한 리소스를 찾을 수 없습니다.',
      details,
      HttpStatus.NOT_FOUND,
    );
  }
}

export class UnauthorizedException extends ApiException {
  constructor(details?: Record<string, any>) {
    super(
      ErrorCode.UNAUTHORIZED,
      '인증이 필요합니다.',
      details,
      HttpStatus.UNAUTHORIZED,
    );
  }
}

export class ForbiddenException extends ApiException {
  constructor(details?: Record<string, any>) {
    super(
      ErrorCode.FORBIDDEN,
      '해당 작업을 수행할 권한이 없습니다.',
      details,
      HttpStatus.FORBIDDEN,
    );
  }
}

export class ConflictException extends ApiException {
  constructor(details?: Record<string, any>) {
    super(
      ErrorCode.CONFLICT,
      '리소스 충돌이 발생했습니다.',
      details,
      HttpStatus.CONFLICT,
    );
  }
}

export class InternalServerException extends ApiException {
  constructor(
    message = '서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.',
  ) {
    super(
      ErrorCode.INTERNAL_ERROR,
      message,
      undefined,
      HttpStatus.INTERNAL_SERVER_ERROR,
    );
  }
}
