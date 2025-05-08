import { ArgumentsHost, Catch, ForbiddenException } from "@nestjs/common";
import { Response } from "express";

import { ErrorCode } from "src/product/presentation/dto/Error.dto";

@Catch(ForbiddenException)
export default class ForbiddenExceptionFilter {
  catch(exception: ForbiddenException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();

    const status = exception.getStatus();

    response.status(status).json({
      success: false,
      error: {
        code: ErrorCode.FORBIDDEN,
        message: "해당 작업을 수행할 권한이 없습니다.",
      },
    });
  }
}
