import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ImageBodyDTO from "./ImageBody.dto";

describe("ImageBodyDTO", () => {
  const validateDTO = async (dto: Partial<ImageBodyDTO>) => {
    const instance = plainToInstance(ImageBodyDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<ImageBodyDTO> = {
      url: "https://example.com/images/sofa3.jpg",
      alt_text: "네이비 소파 측면",
      is_primary: false,
      display_order: 3,
      option_id: 35,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const dto: Partial<ImageBodyDTO> = {
      alt_text: "네이비 소파 측면",
      is_primary: false,
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("is_primary 필드가 boolean이 아닌 경우 검증 실패", async () => {
    const dto: Partial<ImageBodyDTO> = {
      url: "https://example.com/images/sofa3.jpg",
      alt_text: "네이비 소파 측면",
      is_primary: "true" as unknown as boolean,
      display_order: 3,
      option_id: 35,
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
    expect(Object.keys(errors[0]!)).toContain("isBoolean");
  });

  it("display_order 필드가 정수가 아닌 경우 검증 실패", async () => {
    const dto: Partial<ImageBodyDTO> = {
      url: "https://example.com/images/sofa3.jpg",
      alt_text: "네이비 소파 측면",
      is_primary: false,
      display_order: "three" as unknown as number,
      option_id: 35,
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
    expect(Object.keys(errors[0]!)).toContain("isInt");
  });

  it("option_id 필드가 정수가 아닌 경우 검증 실패", async () => {
    const dto: Partial<ImageBodyDTO> = {
      url: "https://example.com/images/sofa3.jpg",
      alt_text: "네이비 소파 측면",
      is_primary: false,
      display_order: 3,
      option_id: "thirty-five" as unknown as number,
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
    expect(Object.keys(errors[0]!)).toContain("isInt");
  });
});
