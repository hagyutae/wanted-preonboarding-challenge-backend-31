import getValidateDTO from "src/__test-utils__/getValidateDTO";

import ReviewDTO from "../model/Review.dto";
import PaginationSummaryDTO from "./PaginationSummary.dto";
import ReviewResponseBundleDTO from "./ReviewResponseBundle.dto";
import ReviewSummaryDTO from "./ReviewSummary.dto";

describe("ReviewResponseBundleDTO", () => {
  const validateDTO = getValidateDTO(ReviewResponseBundleDTO);

  const validData: Partial<ReviewResponseBundleDTO> = {
    items: [{ content: "리뷰 내용" }] as ReviewDTO[],
    summary: { average_rating: 0 } as ReviewSummaryDTO,
    pagination: {
      total_items: 100,
      total_pages: 10,
      current_page: 1,
      per_page: 10,
    } as PaginationSummaryDTO,
  };

  it("items 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, items: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("items");
  });

  it("summary 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, summary: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("summary");
  });

  it("pagination 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, pagination: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("pagination");
  });

  it("items 필드가 배열이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      items: "not_an_array" as unknown as ReviewDTO[],
    };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("items");
  });
});
