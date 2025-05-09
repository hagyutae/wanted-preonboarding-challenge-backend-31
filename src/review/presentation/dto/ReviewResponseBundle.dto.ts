import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsDefined, ValidateNested } from "class-validator";

import { PaginationSummaryDTO } from "@libs/common/dto";
import ReviewDTO from "./Review.dto";
import ReviewSummaryDTO from "./ReviewSummary.dto";

export default class ReviewResponseBundleDTO {
  @ApiProperty({ description: "리뷰 목록", type: [ReviewDTO] })
  @IsArray()
  @ValidateNested()
  @Type(() => ReviewDTO)
  items: ReviewDTO[];

  @ApiProperty({ description: "리뷰 요약 정보", type: ReviewSummaryDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => ReviewSummaryDTO)
  summary: ReviewSummaryDTO;

  @ApiProperty({ description: "페이지네이션 정보", type: PaginationSummaryDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => PaginationSummaryDTO)
  pagination: PaginationSummaryDTO;
}
