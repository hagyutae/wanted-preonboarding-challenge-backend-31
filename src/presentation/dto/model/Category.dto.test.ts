import getValidateDTO from "src/__test-utils__/getValidateDTO";

import CategoryDTO from "./Category.dto";

describe("CategoryDTO", () => {
  const validateDTO = getValidateDTO(CategoryDTO);

  const validData: Partial<CategoryDTO> = {
    id: 5,
    name: "소파",
    slug: "sofa",
    is_primary: true,
    description: "다양한 스타일의 소파",
    level: 3,
    image_url: "https://example.com/categories/sofa.jpg",
    parent: {
      id: 2,
      name: "거실 가구",
      slug: "living-room",
    },
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(7);
    expect(errors).toContain("id");
    expect(errors).toContain("name");
    expect(errors).toContain("slug");
    expect(errors).toContain("is_primary");
    expect(errors).toContain("description");
    expect(errors).toContain("level");
    expect(errors).toContain("image_url");
  });

  it("image_url 필드가 URL이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      image_url: "invalid-url",
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("image_url");
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

  it("level 필드가 정수가 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      level: "three" as unknown as number,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("level");
  });

  it("parent 필드가 올바르지 않은 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      parent: {
        id: "two" as unknown as number,
        name: "거실 가구",
        slug: "living-room",
      },
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("parent");
  });
});
