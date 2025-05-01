import { ApiProperty } from "@nestjs/swagger";

export default class PaginationSummaryDTO {
  @ApiProperty({
    description: "총 항목 수",
    example: 50,
  })
  total_items: number;

  @ApiProperty({
    description: "총 페이지 수",
    example: 5,
  })
  total_pages: number;

  @ApiProperty({
    description: "현재 페이지",
    example: 1,
  })
  current_page: number;

  @ApiProperty({
    description: "페이지당 항목 수",
    example: 10,
  })
  per_page: number;
}
