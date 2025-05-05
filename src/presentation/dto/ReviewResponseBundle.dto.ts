import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, ValidateNested } from "class-validator";

import PaginationSummaryDTO from "./PaginationSummary.dto";
import ReviewDTO from "./Review.dto";
import ReviewSummaryDTO from "./ReviewSummary.dto";

export default class ReviewResponseBundle {
  @ApiProperty({ description: "리뷰 목록", type: [ReviewDTO] })
  @IsArray()
  @ValidateNested()
  @Type(() => ReviewDTO)
  items: ReviewDTO[];

  @ApiProperty({ description: "리뷰 요약 정보", type: ReviewSummaryDTO })
  @ValidateNested()
  @Type(() => ReviewSummaryDTO)
  summary: ReviewSummaryDTO;

  @ApiProperty({ description: "페이지네이션 정보", type: PaginationSummaryDTO })
  @ValidateNested()
  @Type(() => PaginationSummaryDTO)
  pagination: PaginationSummaryDTO;
}
