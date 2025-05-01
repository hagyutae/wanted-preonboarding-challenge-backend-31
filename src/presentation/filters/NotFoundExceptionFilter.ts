import { Catch, NotFoundException, ExceptionFilter, ArgumentsHost } from "@nestjs/common";
import { Response } from "express";

import { ErrorCode } from "../dto/Error.dto";

@Catch(NotFoundException)
export default class NotFoundExceptionFilter implements ExceptionFilter {
  catch(exception: NotFoundException, host: ArgumentsHost) {
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
        code: ErrorCode.NOT_FOUND,
        message,
      },
    });
  }
}
