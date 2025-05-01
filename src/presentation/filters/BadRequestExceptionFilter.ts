import { ExceptionFilter, Catch, ArgumentsHost, BadRequestException } from "@nestjs/common";
import { Response } from "express";

import { ErrorCode } from "../dto/Error.dto";

@Catch(BadRequestException)
export default class BadRequestExceptionFilter implements ExceptionFilter {
  catch(exception: BadRequestException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();

    const status = exception.getStatus();
    const exception_response = exception.getResponse();
    const message =
      typeof exception_response === "string"
        ? exception_response
        : (exception_response as any).message;

    response.status(status).json({
      success: false,
      error: {
        code: ErrorCode.INVALID_INPUT,
        message,
      },
    });
  }
}
