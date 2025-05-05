import getValidateDTO from "src/__test-utils__/getValidateDTO";

import ProductResponseDTO from "./ProductResponse.dto";

describe("ProductResponseDTO", () => {
  const validateDTO = getValidateDTO(ProductResponseDTO);

  const validData: Partial<ProductResponseDTO> = {
    id: 123,
    name: "슈퍼 편안한 소파",
    slug: "super-comfortable-sofa",
    created_at: new Date("2025-04-10T09:30:00Z"),
    updated_at: new Date("2025-04-14T10:15:00Z"),
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(5);
    expect(errors).toContain("id");
    expect(errors).toContain("name");
    expect(errors).toContain("slug");
    expect(errors).toContain("created_at");
    expect(errors).toContain("updated_at");
  });

  it("created_at 필드가 날짜 형식이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, created_at: "not_a_date" as unknown as Date };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("created_at");
  });

  it("updated_at 필드가 날짜 형식이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, updated_at: "not_a_date" as unknown as Date };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("updated_at");
  });

  it("id 필드가 숫자가 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, id: "not_a_number" as unknown as number };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("id");
  });
});
