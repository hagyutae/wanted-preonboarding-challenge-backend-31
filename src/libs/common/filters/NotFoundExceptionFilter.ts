import { Catch, NotFoundException, ExceptionFilter, ArgumentsHost } from "@nestjs/common";
import { Response } from "express";

import { ErrorCode } from "src/product/presentation/dto/Error.dto";

@Catch(NotFoundException)
export default class NotFoundExceptionFilter implements ExceptionFilter {
  catch(exception: NotFoundException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();

    const status = exception.getStatus();

    const { message, details } = exception.getResponse() as {
      message: string;
      details?: string[];
    };

    response.status(status).json({
      success: false,
      error: {
        code: ErrorCode.NOT_FOUND,
        message,
        details,
      },
    });
  }
}
