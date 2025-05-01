import { ApiProperty } from "@nestjs/swagger";

export default class ProductResponseDTO {
  @ApiProperty({ description: "상품 ID", example: 1 })
  id: number;

  @ApiProperty({ description: "상품 이름", example: "소파" })
  name: string;

  @ApiProperty({ description: "슬러그", example: "super-comfortable-sofa" })
  slug: string;

  @ApiProperty({ description: "생성 일시", example: "2025-04-14T09:30:00Z" })
  created_at?: Date;

  @ApiProperty({ description: "수정 일시", example: "2025-04-14T09:30:00Z" })
  updated_at: Date;
}
