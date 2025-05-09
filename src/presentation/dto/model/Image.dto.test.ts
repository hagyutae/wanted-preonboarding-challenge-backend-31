import getValidateDTO from "src/__test-utils__/getValidateDTO";

import ImageDTO from "./Image.dto";

describe("ImageDTO", () => {
  const validateDTO = getValidateDTO(ImageDTO);

  const validData: Partial<ImageDTO> = {
    url: "https://example.com/images/sofa3.jpg",
    alt_text: "네이비 소파 측면",
    is_primary: false,
    display_order: 3,
    option_id: 35,
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(4);
    expect(errors).toContain("url");
    expect(errors).toContain("alt_text");
    expect(errors).toContain("is_primary");
    expect(errors).toContain("display_order");
  });

  it("is_primary 필드가 boolean이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      is_primary: "true" as unknown as boolean,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("is_primary");
  });

  it("display_order 필드가 정수가 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      display_order: "three" as unknown as number,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("display_order");
  });

  it("option_id 필드가 정수가 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      option_id: "thirty-five" as unknown as number,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("option_id");
  });
});
