import { ApiPropertyOptional } from "@nestjs/swagger";
import { IsInt, IsOptional, Matches, Max, Min } from "class-validator";

export default class ReviewQueryDTO {
  @ApiPropertyOptional({ description: "페이지 번호", example: 1 })
  @IsOptional()
  @IsInt()
  @Min(1)
  page?: number;

  @ApiPropertyOptional({ description: "페이지당 아이템 수", example: 10 })
  @IsOptional()
  @IsInt()
  @Min(1)
  perPage?: number;

  @ApiPropertyOptional({
    description: "정렬 기준. 형식: {필드}:{asc|desc}",
    example: "created_at:desc",
  })
  @Matches(/^(\w+:(asc|desc))(,\w+:(asc|desc))*$/, {
    message: "sort 형식은 field:asc|desc 이어야 합니다.",
  })
  @IsOptional()
  sort?: string = "created_at:desc";

  @ApiPropertyOptional({ description: "평점 필터 (1-5)", example: 4 })
  @IsOptional()
  @IsInt()
  @Min(1)
  @Max(5)
  rating?: number;
}
