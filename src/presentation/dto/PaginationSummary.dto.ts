import { ApiProperty } from "@nestjs/swagger";
import { IsInt, Min } from "class-validator";

export default class PaginationSummaryDTO {
  @ApiProperty({
    description: "총 항목 수",
    example: 50,
  })
  @IsInt()
  @Min(1)
  total_items: number;

  @ApiProperty({
    description: "총 페이지 수",
    example: 5,
  })
  @IsInt()
  @Min(1)
  total_pages: number;

  @ApiProperty({
    description: "현재 페이지",
    example: 1,
  })
  @IsInt()
  @Min(1)
  current_page: number;

  @ApiProperty({
    description: "페이지당 항목 수",
    example: 10,
  })
  @IsInt()
  @Min(1)
  per_page: number;
}
