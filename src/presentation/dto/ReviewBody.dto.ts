import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsNumber } from "class-validator";

export default class ReviewBodyDTO {
  @ApiProperty({ description: "평점", example: 5 })
  @Type(() => Number)
  @IsNumber()
  rating: number;

  @ApiProperty({ description: "제목", example: "완벽한 소파입니다!" })
  title: string;

  @ApiProperty({
    description: "내용",
    example: "배송도 빠르고 품질도 매우 좋습니다. 색상도 사진과 동일하고 조립도 쉬웠어요.",
  })
  content: string;
}
