import { ApiProperty } from "@nestjs/swagger";

export default class ImageBodyDTO {
  @ApiProperty({ description: "이미지 URL", example: "https://example.com/images/sofa3.jpg" })
  url: string;

  @ApiProperty({ description: "이미지 대체 텍스트", example: "네이비 소파 측면" })
  alt_text: string;

  @ApiProperty({ description: "대표 이미지 여부", example: false })
  is_primary: boolean;

  @ApiProperty({ description: "이미지 표시 순서", example: 3 })
  display_order: number;

  @ApiProperty({ description: "옵션 ID", example: 35 })
  option_id: number;
}
