import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsBoolean, IsEnum, IsOptional, ValidateNested } from "class-validator";

import ErrorCode from "../constants/ErrorCode";

export class ErrorDetails {
  [key: string]: string;
}

class ErrorObject {
  @ApiProperty({
    description: "에러 발생 시 응답은 다음 형식을 따릅니다",
    enum: ErrorCode,
  })
  @IsEnum(ErrorCode)
  code: ErrorCode;

  @ApiProperty({ description: "에러 메시지", example: "에러가 발생했습니다." })
  message: string;

  @ApiPropertyOptional({
    description: "추가적인 에러 세부 정보 (선택 사항)",
    type: () => ErrorDetails,
  })
  @IsOptional()
  @ValidateNested()
  @Type(() => ErrorDetails)
  details?: ErrorDetails;
}

export default class ErrorDTO {
  @ApiProperty({ description: "요청 성공 여부", example: false })
  @IsBoolean()
  success: boolean;

  @ApiProperty({
    description: "공통 에러 코드",
    type: () => ErrorObject,
  })
  @ValidateNested()
  @Type(() => ErrorObject)
  error: ErrorObject;
}
