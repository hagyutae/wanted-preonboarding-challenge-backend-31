import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsBoolean, IsInt, IsOptional, IsUrl, Min } from "class-validator";

export default class ImageDTO {
  @ApiPropertyOptional({ description: "이미지 ID", example: 1 })
  @IsInt()
  @IsOptional()
  id?: number;

  @ApiProperty({ description: "이미지 URL", example: "https://example.com/images/sofa3.jpg" })
  @IsUrl()
  url: string;

  @ApiProperty({ description: "이미지 대체 텍스트", example: "네이비 소파 측면" })
  alt_text: string;

  @ApiProperty({ description: "대표 이미지 여부", example: false })
  @IsBoolean()
  is_primary: boolean;

  @ApiProperty({ description: "이미지 표시 순서", example: 3 })
  @IsInt()
  @Min(1)
  display_order: number;

  @ApiProperty({ description: "옵션 ID", example: 35, nullable: true })
  @IsInt()
  option_id?: number | null;
}
