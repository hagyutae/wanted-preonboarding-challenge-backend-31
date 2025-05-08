import getValidateDTO from "__test-utils__/getValidateDTO";

import RatingDTO from "./Rating.dto";

describe("RatingDTO", () => {
  const validateDTO = getValidateDTO(RatingDTO);

  const validData: Partial<RatingDTO> = {
    average: 4.5,
    count: 150,
    distribution: {
      "5": 100,
      "4": 30,
      "3": 15,
      "2": 3,
      "1": 2,
    },
  };

  it("유효한 데이터로 성공적으로 검증되어야 한다", async () => {
    const errors = await validateDTO(validData);

    expect(errors.length).toBe(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(3);
    expect(errors).toContain("average");
    expect(errors).toContain("count");
    expect(errors).toContain("distribution");
  });

  it("average 필드가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, average: -1 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("average");
  });

  it("count 필드가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, count: -10 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("count");
  });
});
