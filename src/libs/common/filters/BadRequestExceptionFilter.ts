import { ExceptionFilter, Catch, ArgumentsHost, BadRequestException } from "@nestjs/common";
import { Response } from "express";

import { ErrorCode } from "src/product/presentation/dto/Error.dto";

@Catch(BadRequestException)
export default class BadRequestExceptionFilter implements ExceptionFilter {
  catch(exception: BadRequestException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();

    const status = exception.getStatus();
    const body = exception.getResponse() as {
      message: string;
      details?: string[];
    };

    response.status(status).json({
      success: false,
      error: {
        code: ErrorCode.INVALID_INPUT,
        message: body.message || "잘못된 요청입니다.",
        details: body.details || [],
      },
    });
  }
}
