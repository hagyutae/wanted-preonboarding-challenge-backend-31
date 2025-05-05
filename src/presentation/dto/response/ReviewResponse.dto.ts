import { PickType } from "@nestjs/swagger";

import ReviewDTO from "../model/Review.dto";

export default class ReviewResponseDTO extends PickType(ReviewDTO, [
  "id",
  "rating",
  "title",
  "content",
  "updated_at",
] as const) {}
