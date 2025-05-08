import {
  Injectable,
  NestInterceptor,
  ExecutionContext,
  CallHandler,
  UnauthorizedException,
  ForbiddenException,
} from "@nestjs/common";
import { Observable } from "rxjs";

import verifier from "./verifier";

@Injectable()
export class JwtInterceptor implements NestInterceptor {
  async intercept(context: ExecutionContext, next: CallHandler): Promise<Observable<any>> {
    const request = context.switchToHttp().getRequest();
    const token = request.headers["authorization"]?.split(" ")[1] as string; // 'Bearer <token>'
    const controller = context.getClass();

    // 메인 컨트롤러는 JWT 검증을 하지 않음
    if (controller.name === "MainController") {
      return next.handle();
    }

    if (!token) {
      throw new UnauthorizedException();
    }

    try {
      const decoded = await verifier.verify(token);
      request.user = decoded;
    } catch (error) {
      throw new ForbiddenException();
    }

    return next.handle();
  }
}
