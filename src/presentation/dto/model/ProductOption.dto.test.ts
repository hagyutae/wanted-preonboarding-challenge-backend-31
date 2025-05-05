import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ProductOptionDTO from "./ProductOption.dto";

describe("ProductOption", () => {
  const validateDTO = async (dto: Partial<ProductOptionDTO>) => {
    const instance = plainToInstance(ProductOptionDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<ProductOptionDTO> = {
    option_group_id: 35,
    name: "네이비 블루",
    additional_price: 25000,
    sku: "SOFA-NVBL",
    stock: 10,
    display_order: 3,
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = { ...validData };
    delete invalidData.name;
    delete invalidData.sku;

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(2);
    expect(errors).toContain("name");
    expect(errors).toContain("sku");
  });

  it("추가 가격이 음수인 경우 검증 실패", async () => {
    const invalidData = { ...validData, additional_price: -10000 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("additional_price");
  });

  it("재고가 음수인 경우 검증 실패", async () => {
    const invalidData = { ...validData, stock: -5 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("stock");
  });
});
