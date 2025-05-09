import getValidateDTO from "__test-utils__/getValidateDTO";

import PaginationSummaryDTO from "./PaginationSummary.dto";

describe("PaginationSummaryDTO", () => {
  const validateDTO = getValidateDTO(PaginationSummaryDTO);

  const validData: Partial<PaginationSummaryDTO> = {
    total_items: 50,
    total_pages: 5,
    current_page: 1,
    per_page: 10,
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(4);
    expect(errors).toContain("total_items");
    expect(errors).toContain("total_pages");
    expect(errors).toContain("current_page");
    expect(errors).toContain("per_page");
  });

  it("total_pages 필드가 0 이하일 경우 검증 실패", async () => {
    const invalidData = { ...validData, total_pages: 0 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("total_pages");
  });

  it("current_page 필드가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, current_page: -1 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("current_page");
  });

  it("total_items 필드가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, total_items: -10 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("total_items");
  });

  it("per_page 필드가 0 이하일 경우 검증 실패", async () => {
    const invalidData = { ...validData, per_page: 0 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("per_page");
  });
});
