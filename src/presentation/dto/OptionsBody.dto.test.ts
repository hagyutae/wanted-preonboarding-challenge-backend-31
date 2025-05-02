import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import OptionsBodyDTO from "./OptionsBody.dto";

describe("OptionsBodyDTO", () => {
  const validateDTO = async (dto: Partial<OptionsBodyDTO>) => {
    const instance = plainToInstance(OptionsBodyDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<OptionsBodyDTO> = {
      option_group_id: 35,
      name: "네이비 블루",
      additional_price: 25000,
      sku: "SOFA-NVBL",
      stock: 10,
      display_order: 3,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const dto: Partial<OptionsBodyDTO> = {
      additional_price: 25000,
      sku: "SOFA-NVBL",
      stock: 10,
    };

    const errors = await validateDTO(dto);

    expect(errors.length).toBeGreaterThan(0);
  });
});
