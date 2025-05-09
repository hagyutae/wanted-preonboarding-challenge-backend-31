import getValidateDTO from "__test-utils__/getValidateDTO";

import ReviewDTO from "./Review.dto";

describe("ReviewDTO", () => {
  const validateDTO = getValidateDTO(ReviewDTO);

  const validData: Partial<ReviewDTO> = {
    id: 1500,
    user: {
      id: 250,
      name: "홍길동",
      avatar_url: "https://example.com/avatars/user250.jpg",
    },
    rating: 5,
    title: "완벽한 소파입니다!",
    content: "배송도 빠르고 품질도 매우 좋습니다. 색상도 사진과 동일하고 조립도 쉬웠어요.",
    created_at: new Date("2025-04-12T15:30:00Z"),
    updated_at: new Date("2025-04-12T15:30:00Z"),
    verified_purchase: true,
    helpful_votes: 12,
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(8);
    expect(errors).toContain("id");
    expect(errors).toContain("rating");
    expect(errors).toContain("title");
    expect(errors).toContain("content");
    expect(errors).toContain("created_at");
    expect(errors).toContain("updated_at");
    expect(errors).toContain("verified_purchase");
    expect(errors).toContain("helpful_votes");
  });

  it("rating 필드가 음수인 경우 검증 실패", async () => {
    const invalidData = { ...validData, rating: -1 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("rating");
  });

  it("created_at 필드가 유효한 날짜가 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, created_at: "invalid-date" as any };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("created_at");
  });

  it("verified_purchase 필드가 boolean이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, verified_purchase: "not-boolean" as any };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("verified_purchase");
  });

  it("helpful_votes 필드가 음수인 경우 검증 실패", async () => {
    const invalidData = { ...validData, helpful_votes: -5 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("helpful_votes");
  });

  it("user 필드가 올바르지 않은 경우 검증 실패", async () => {
    const invalidData = { ...validData, user: { id: "invalid-id" } as any };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("user");
  });
});
