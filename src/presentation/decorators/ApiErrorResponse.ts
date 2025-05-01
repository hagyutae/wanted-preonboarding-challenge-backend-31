import { applyDecorators as apply_decorators } from "@nestjs/common";
import { ApiExtraModels, ApiResponse, getSchemaPath } from "@nestjs/swagger";

import { ErrorDTO, HttpStatusToErrorCodeMap } from "../dto";

export function ApiBadRequestResponse(description = "입력 데이터가 유효하지 않습니다..") {
  return get_apply_decorators(400, description);
}

export function ApiUnauthorizedResponse(description = "인증이 필요합니다.") {
  return get_apply_decorators(401, description);
}

export function ApiForbiddenResponse(description = "해당 작업을 수행할 권한이 없습니다.") {
  return get_apply_decorators(403, description);
}

export function ApiNotFoundResponse(description = "요청한 리소스를 찾을 수 없습니다.") {
  return get_apply_decorators(404, description);
}

export function ApiConflictResponse(description = "리소스 충돌이 발생했습니다.") {
  return get_apply_decorators(409, description);
}

export function ApiInternalServerErrorResponse(description = "서버 내부 오류가 발생했습니다.") {
  return get_apply_decorators(500, description);
}

export default function ApiErrorResponse() {
  return apply_decorators(
    ApiBadRequestResponse(),
    ApiUnauthorizedResponse(),
    ApiForbiddenResponse(),
    ApiNotFoundResponse(),
    ApiConflictResponse(),
    ApiInternalServerErrorResponse(),
  );
}

function get_apply_decorators(status: number, description: string) {
  const example: ErrorDTO = {
    success: false,
    error: {
      code: HttpStatusToErrorCodeMap[status],
      message: description,
      details: {},
    },
  };

  return apply_decorators(
    ApiExtraModels(ErrorDTO),
    ApiResponse({
      status,
      description,
      schema: {
        allOf: [{ $ref: getSchemaPath(ErrorDTO) }, { example }],
      },
    }),
  );
}
