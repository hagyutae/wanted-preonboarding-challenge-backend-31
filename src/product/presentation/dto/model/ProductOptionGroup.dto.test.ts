import getValidateDTO from "__test-utils__/getValidateDTO";

import ProductOptionDTO from "./ProductOption.dto";
import ProductOptionGroupDTO from "./ProductOptionGroup.dto";

describe("ProductOptionGroupDTO", () => {
  const validateDTO = getValidateDTO(ProductOptionGroupDTO);

  const validOption: ProductOptionDTO = {
    option_group_id: 35,
    name: "네이비 블루",
    additional_price: 25000,
    sku: "SOFA-NVBL",
    stock: 10,
    display_order: 3,
  };

  const validData: Partial<ProductOptionGroupDTO> = {
    id: 1,
    name: "색상",
    display_order: 1,
    options: [validOption],
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(3);
    expect(errors).toContain("name");
    expect(errors).toContain("display_order");
    expect(errors).toContain("options");
  });

  it("display_order 필드가 1보다 작은 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      display_order: 0,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("display_order");
  });

  it("options 필드가 배열이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      options: "invalid-options" as unknown as ProductOptionDTO[],
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("options");
  });

  it("options 배열의 요소가 유효하지 않은 경우 검증 실패", async () => {
    const invalidOption = { ...validOption, additional_price: -1000 };
    const invalidData = {
      ...validData,
      options: [invalidOption],
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("options");
  });
});
