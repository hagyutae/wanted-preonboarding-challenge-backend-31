import { Catch, ExceptionFilter, ArgumentsHost } from "@nestjs/common";
import { Response } from "express";
import { QueryFailedError } from "typeorm";

import { ErrorCode } from "../dto/Error.dto";

@Catch(QueryFailedError)
export default class QueryFailedExceptionFilter implements ExceptionFilter {
  catch(exception: QueryFailedError, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();

    const driver_error = exception.driverError as any;

    // 23505: PostgreSQL에서 unique constraint 위반
    if (driver_error.code === "23505") {
      const [, field, value] = driver_error.detail.match(
        /Key \((.+?)\)=\((.+?)\) already exists\./,
      );

      response.status(409).json({
        success: false,
        error: {
          code: ErrorCode.CONFLICT,
          message: "리소스 충돌이 발생했습니다.",
          details: {
            field,
            value,
            message: `${field}(${value})는 이미 존재합니다.`,
          },
        },
      });
    } else {
      response.status(500).json({
        success: false,
        error: {
          code: ErrorCode.INTERNAL_ERROR,
          message: "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.",
        },
      });
    }
  }
}
