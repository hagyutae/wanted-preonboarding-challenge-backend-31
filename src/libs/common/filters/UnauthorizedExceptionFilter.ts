import { ArgumentsHost, Catch, UnauthorizedException } from "@nestjs/common";
import { Response } from "express";

import { ErrorCode } from "src/product/presentation/dto/Error.dto";

@Catch(UnauthorizedException)
export default class UnauthorizedExceptionFilter {
  catch(exception: UnauthorizedException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();

    const status = exception.getStatus();

    response.status(status).json({
      success: false,
      error: {
        code: ErrorCode.UNAUTHORIZED,
        message: "인증이 필요합니다.",
      },
    });
  }
}
