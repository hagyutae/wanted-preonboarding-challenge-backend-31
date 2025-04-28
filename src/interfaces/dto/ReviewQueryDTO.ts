import { ApiPropertyOptional } from "@nestjs/swagger";

export default class ReviewQueryDTO {
  @ApiPropertyOptional({
    description: "페이지 번호",
    example: 1,
  })
  page?: number;

  @ApiPropertyOptional({
    description: "페이지당 아이템 수",
    example: 10,
  })
  perPage?: number;

  @ApiPropertyOptional({
    description: "정렬 기준. 형식: {필드}:{asc|desc}",
    example: "created_at:desc",
  })
  sort?: string;

  @ApiPropertyOptional({
    description: "평점 필터 (1-5)",
    example: 4,
  })
  rating?: number;
}
