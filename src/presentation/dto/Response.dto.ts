import { ApiProperty } from "@nestjs/swagger";
import { IsBoolean, IsOptional } from "class-validator";

import { BooleanString } from "../decorators";

export default class ResponseDTO {
  @ApiProperty({ description: "요청 성공 여부", example: true })
  @BooleanString()
  @IsBoolean()
  success: boolean;

  @ApiProperty({ description: "응답 데이터", example: {} })
  data: any;

  @ApiProperty({
    description: "응답 메시지",
    example: "요청이 성공적으로 처리되었습니다.",
    required: false,
  })
  @IsOptional()
  message?: string;
}
