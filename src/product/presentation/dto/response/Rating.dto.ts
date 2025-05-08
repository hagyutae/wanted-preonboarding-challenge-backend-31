import { ApiProperty } from "@nestjs/swagger";
import { IsDefined, IsInt, IsNumber, Min } from "class-validator";

export default class RatingDTO {
  @ApiProperty({ description: "평균 평점", example: 4.5 })
  @IsNumber()
  @Min(0)
  average: number;

  @ApiProperty({ description: "총 리뷰 수", example: 150 })
  @IsInt()
  @Min(0)
  count: number;

  @ApiProperty({
    description: "별점 분포",
    example: {
      "5": 100,
      "4": 30,
      "3": 15,
      "2": 3,
      "1": 2,
    },
  })
  @IsDefined()
  distribution: {
    [key: string]: number;
  };
}
