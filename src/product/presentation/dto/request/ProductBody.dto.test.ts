import getValidateDTO from "__test-utils__/getValidateDTO";

import ProductBodyDTO from "./ProductBody.dto";

describe("ProductBodyDTO", () => {
  const validateDTO = getValidateDTO(ProductBodyDTO);

  const validData: Partial<ProductBodyDTO> = {
    name: "슈퍼 편안한 소파",
    slug: "super-comfortable-sofa",
    short_description: "최고급 소재로 만든 편안한 소파",
    full_description: "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
    seller_id: 1,
    brand_id: 2,
    status: "ACTIVE",
    detail: {
      weight: 25.5,
      dimensions: {
        width: 200,
        height: 85,
        depth: 90,
      },
      materials: "가죽, 목재, 폼",
      country_of_origin: "대한민국",
      warranty_info: "2년 품질 보증",
      care_instructions: "마른 천으로 표면을 닦아주세요",
      additional_info: {
        assembly_required: true,
        assembly_time: "30분",
      },
    },
    price: {
      base_price: 599000,
      sale_price: 499000,
      cost_price: 350000,
      currency: "KRW",
      tax_rate: 10,
    },
    categories: [
      { category_id: 5, is_primary: true },
      { category_id: 6, is_primary: false },
    ],
    option_groups: [
      {
        name: "색상",
        display_order: 1,
        options: [
          { name: "브라운", additional_price: 0, sku: "SOFA-BRN", stock: 10, display_order: 1 },
        ],
      },
    ],
    images: [
      {
        url: "https://example.com/images/sofa1.jpg",
        alt_text: "브라운 소파 정면",
        is_primary: true,
        display_order: 1,
        option_id: null,
      },
    ],
    tags: [1, 4, 7],
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(13);
    expect(errors).toContain("name");
    expect(errors).toContain("slug");
    expect(errors).toContain("short_description");
    expect(errors).toContain("full_description");
    expect(errors).toContain("seller_id");
    expect(errors).toContain("brand_id");
    expect(errors).toContain("status");
    expect(errors).toContain("detail");
    expect(errors).toContain("price");
    expect(errors).toContain("categories");
    expect(errors).toContain("option_groups");
    expect(errors).toContain("images");
    expect(errors).toContain("tags");
  });

  it("status 필드가 허용되지 않은 값일 경우 검증 실패", async () => {
    const invalidData = { ...validData, status: "INVALID_STATUS" };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("status");
  });

  it("categories 필드가 잘못된 형식일 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      categories: [{ category_id: "not_a_number" as unknown as number, is_primary: true }],
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("categories");
  });

  it("tags 필드가 숫자가 아닌 값이 포함된 경우 검증 실패", async () => {
    const invalidData = { ...validData, tags: [1, "not_a_number", 7] as unknown as number[] };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("tags");
  });
});
