import getValidateDTO from "__test-utils__/getValidateDTO";

import ProductOptionDTO from "./ProductOption.dto";

describe("ProductOption", () => {
  const validateDTO = getValidateDTO(ProductOptionDTO);

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
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(5);
    expect(errors).toContain("name");
    expect(errors).toContain("additional_price");
    expect(errors).toContain("sku");
    expect(errors).toContain("stock");
    expect(errors).toContain("display_order");
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
