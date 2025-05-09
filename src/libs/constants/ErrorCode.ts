enum ErrorCode {
  INVALID_INPUT = "INVALID_INPUT", // 잘못된 입력 데이터 (400)
  RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND", // 요청한 리소스를 찾을 수 없음 (404)
  UNAUTHORIZED = "UNAUTHORIZED", // 인증되지 않은 요청 (401)
  FORBIDDEN = "FORBIDDEN", // 권한이 없는 요청 (403)
  NOT_FOUND = "NOT_FOUND", // 요청한 리소스를 찾을 수 없음 (404)
  CONFLICT = "CONFLICT", // 리소스 충돌 발생 (409)
  INTERNAL_ERROR = "INTERNAL_ERROR", // 서버 내부 오류 (500)
}

export const HttpStatusToErrorCodeMap: Record<number, ErrorCode> = {
  400: ErrorCode.INVALID_INPUT,
  401: ErrorCode.UNAUTHORIZED,
  403: ErrorCode.FORBIDDEN,
  404: ErrorCode.RESOURCE_NOT_FOUND,
  409: ErrorCode.CONFLICT,
  500: ErrorCode.INTERNAL_ERROR,
};

export default ErrorCode;
