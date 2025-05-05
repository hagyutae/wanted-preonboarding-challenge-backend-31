import { HttpException, HttpStatus } from '@nestjs/common';
import { ErrorCode, ErrorDetails } from '../types/error.types';

export class AppException extends HttpException {
  constructor(errorDetails: ErrorDetails) {
    const status = AppException.getHttpStatus(errorDetails.code);
    super(
      {
        success: false,
        error: {
          code: errorDetails.code,
          message: errorDetails.message,
          details: errorDetails.details,
        },
      },
      status,
    );
  }

  private static getHttpStatus(code: ErrorCode): HttpStatus {
    switch (code) {
      case ErrorCode.INVALID_INPUT:
        return HttpStatus.BAD_REQUEST;
      case ErrorCode.RESOURCE_NOT_FOUND:
        return HttpStatus.NOT_FOUND;
      case ErrorCode.UNAUTHORIZED:
        return HttpStatus.UNAUTHORIZED;
      case ErrorCode.FORBIDDEN:
        return HttpStatus.FORBIDDEN;
      case ErrorCode.CONFLICT:
        return HttpStatus.CONFLICT;
      case ErrorCode.INTERNAL_ERROR:
      default:
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
