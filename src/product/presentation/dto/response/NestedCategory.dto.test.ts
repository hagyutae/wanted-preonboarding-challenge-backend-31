import getValidateDTO from "__test-utils__/getValidateDTO";

import NestedCategoryDTO from "./NestedCategory.dto";

describe("NestedCategoryDTO", () => {
  const validateDTO = getValidateDTO(NestedCategoryDTO);

  const validData: Partial<NestedCategoryDTO> = {
    id: 1,
    name: "Category Name",
    slug: "category-name",
    description: "Category description",
    level: 1,
    image_url: "http://example.com/image.jpg",
    children: [
      {
        id: 2,
        name: "Subcategory Name",
        slug: "subcategory-name",
        description: "Subcategory description",
        level: 2,
        image_url: "http://example.com/subimage.jpg",
      },
    ],
  };

  it("유효한 데이터로 검증 성공", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(6);
    expect(errors).toContain("id");
    expect(errors).toContain("name");
    expect(errors).toContain("slug");
    expect(errors).toContain("description");
    expect(errors).toContain("level");
    expect(errors).toContain("image_url");
  });

  it("children 필드가 배열이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, children: "not_an_array" as any };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("children");
  });

  it("children 배열의 요소가 유효하지 않은 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      children: [
        {
          id: "invalid_id",
          name: 123,
        } as any,
      ],
    };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("children");
  });

  it("level 필드가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, level: -1 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("level");
  });

  it("image_url 필드가 유효한 URL이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, image_url: "invalid_url" };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("image_url");
  });
});
