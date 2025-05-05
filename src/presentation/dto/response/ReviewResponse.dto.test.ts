import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ReviewResponseDTO from "./ReviewResponse.dto";

describe("ReviewResponseDTO", () => {
  const validateDTO = async (dto: Partial<ReviewResponseDTO>) => {
    const instance = plainToInstance(ReviewResponseDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<ReviewResponseDTO> = {
    id: 1,
    rating: 5,
    title: "완벽한 소파입니다!",
    content: "배송도 빠르고 품질도 매우 좋습니다. 색상도 사진과 동일하고 조립도 쉬웠어요.",
    updated_at: new Date("2025-04-12T15:30:00Z"),
  };

  it("유효한 데이터로 성공적으로 검증되어야 한다", async () => {
    const errors = await validateDTO(validData);

    expect(errors.length).toBe(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(5);
    expect(errors).toContain("id");
    expect(errors).toContain("rating");
    expect(errors).toContain("title");
    expect(errors).toContain("content");
    expect(errors).toContain("updated_at");
  });
});
