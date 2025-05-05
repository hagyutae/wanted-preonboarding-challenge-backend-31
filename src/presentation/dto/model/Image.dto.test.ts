import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ImageDTO from "./Image.dto";

describe("ImageDTO", () => {
  const validateDTO = async (dto: Partial<ImageDTO>) => {
    const instance = plainToInstance(ImageDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

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
    const invalidData = { ...validData };
    delete invalidData.url;
    delete invalidData.alt_text;

    const errors = await validateDTO(invalidData);

    expect(errors).not.toHaveLength(0);
  });

  it("is_primary 필드가 boolean이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      is_primary: "true" as unknown as boolean,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).not.toHaveLength(0);
    expect(Object.keys(errors[0]!)).toContain("isBoolean");
  });

  it("display_order 필드가 정수가 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      display_order: "three" as unknown as number,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).not.toHaveLength(0);
    expect(Object.keys(errors[0]!)).toContain("isInt");
  });

  it("option_id 필드가 정수가 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      option_id: "thirty-five" as unknown as number,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).not.toHaveLength(0);
    expect(Object.keys(errors[0]!)).toContain("isInt");
  });
});
