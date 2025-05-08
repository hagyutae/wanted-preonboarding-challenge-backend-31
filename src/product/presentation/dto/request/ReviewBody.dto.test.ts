import getValidateDTO from "src/__test-utils__/getValidateDTO";

import ReviewBodyDTO from "./ReviewBody.dto";

describe("ReviewBodyDTO", () => {
  const validateDTO = getValidateDTO(ReviewBodyDTO);

  const validData: Partial<ReviewBodyDTO> = {
    rating: 5,
    title: "완벽한 소파입니다!",
    content: "배송도 빠르고 품질도 매우 좋습니다. 색상도 사진과 동일하고 조립도 쉬웠어요.",
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(3);
    expect(errors).toContain("rating");
    expect(errors).toContain("title");
    expect(errors).toContain("content");
  });

  it("유효하지 않은 평점은 검증 실패", async () => {
    const invalidData = {
      ...validData,
      rating: 6,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("rating");
  });
});
