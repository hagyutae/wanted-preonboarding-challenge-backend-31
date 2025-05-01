import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsBoolean, IsInt } from "class-validator";

import { BooleanString } from "../decorators";

export default class ImageBodyDTO {
  @ApiProperty({ description: "이미지 URL", example: "https://example.com/images/sofa3.jpg" })
  url: string;

  @ApiProperty({ description: "이미지 대체 텍스트", example: "네이비 소파 측면" })
  alt_text: string;

  @ApiProperty({ description: "대표 이미지 여부", example: false })
  @BooleanString()
  @IsBoolean()
  is_primary: boolean;

  @ApiProperty({ description: "이미지 표시 순서", example: 3 })
  @Type(() => Number)
  @IsInt()
  display_order: number;

  @ApiProperty({ description: "옵션 ID", example: 35 })
  @Type(() => Number)
  @IsInt()
  option_id: number;
}
