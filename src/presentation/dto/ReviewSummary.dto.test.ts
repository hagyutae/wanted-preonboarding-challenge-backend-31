import ReviewSummaryDTO from "./ReviewSummary.dto";

describe("ReviewSummaryDTO", () => {
  it("ReviewSummaryDTO 기본값 테스트", () => {
    const dto = new ReviewSummaryDTO();

    expect(dto.average_rating).toBeUndefined();
    expect(dto.total_count).toBeUndefined();
    expect(dto.distribution).toBeUndefined();
  });
});
