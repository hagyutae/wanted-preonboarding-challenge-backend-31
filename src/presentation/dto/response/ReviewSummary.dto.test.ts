import { validate } from "class-validator";

import ReviewSummaryDTO from "./ReviewSummary.dto";

describe("ReviewSummaryDTO", () => {
  it("ReviewSummaryDTO 기본값 테스트", () => {
    const dto = new ReviewSummaryDTO();

    expect(dto.average_rating).toBeUndefined();
    expect(dto.total_count).toBeUndefined();
    expect(dto.distribution).toBeUndefined();
  });

  it("ReviewSummaryDTO 유효하지 않은 값 테스트", async () => {
    const dto = new ReviewSummaryDTO();
    dto.average_rating = 6;

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
  });
});
