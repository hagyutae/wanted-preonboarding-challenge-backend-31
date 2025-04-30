import { ApiProperty } from "@nestjs/swagger";

export interface ErrorDetails {
  [key: string]: any;
}

export enum ErrorCode {
  INVALID_INPUT = "INVALID_INPUT", // 잘못된 입력 데이터 (400)
  RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND", // 요청한 리소스를 찾을 수 없음 (404)
  UNAUTHORIZED = "UNAUTHORIZED", // 인증되지 않은 요청 (401)
  FORBIDDEN = "FORBIDDEN", // 권한이 없는 요청 (403)
  CONFLICT = "CONFLICT", // 리소스 충돌 발생 (409)
  INTERNAL_ERROR = "INTERNAL_ERROR", // 서버 내부 오류 (500)
}

class ErrorObject {
  @ApiProperty({
    enum: ErrorCode,
    description: "에러 발생 시 응답은 다음 형식을 따릅니다",
  })
  code: ErrorCode;

  @ApiProperty({ description: "에러 메시지", example: "에러가 발생했습니다." })
  message: string;

  @ApiProperty({
    description: "추가적인 에러 세부 정보 (선택 사항)",
    required: false,
    type: Object,
  })
  details?: ErrorDetails;
}

export default class ErrorDTO {
  @ApiProperty({ description: "요청 성공 여부", example: false })
  success: boolean;

  @ApiProperty({
    description: "공통 에러 코드",
    type: () => ErrorObject,
  })
  error: ErrorObject;
}
