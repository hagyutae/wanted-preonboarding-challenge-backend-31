import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";
import NestedCategoryDTO from "./NestedCategory.dto";

describe("NestedCategoryDTO", () => {
  const validData = {
    id: 1,
    name: "카테고리 이름",
    slug: "category-slug",
    description: "카테고리 설명",
    level: 2,
    image_url: "http://example.com/image.jpg",
    children: [
      {
        id: 2,
        name: "서브 카테고리",
        slug: "sub-category-slug",
        description: "서브 카테고리 설명",
        level: 3,
        image_url: "http://example.com/sub-image.jpg",
      },
    ],
  };

  it("유효한 데이터로 유효성 검사가 통과", async () => {
    const dto = plainToInstance(NestedCategoryDTO, validData);
    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("유효하지 않은 데이터로 유효성 검사가 실패", async () => {
    const invalidData = {
      id: "invalid-id", // 숫자가 아님
      name: 123, // 문자열이 아님
      slug: null, // null 값
      description: 456, // 문자열이 아님
      level: "invalid-level", // 숫자가 아님
      image_url: 789, // 문자열이 아님
      children: "invalid-children", // 배열이 아님
    };

    const dto = plainToInstance(NestedCategoryDTO, invalidData);
    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors.map((error) => error.property)).not.toEqual(
      expect.arrayContaining([
        "id",
        "name",
        "slug",
        "description",
        "level",
        "image_url",
        "children",
      ]),
    );
  });

  it("중첩된 children 필드가 유효하지 않을 경우 유효성 검사가 실패", async () => {
    const invalidChildrenData = {
      ...validData,
      children: [
        {
          id: "invalid-id", // 숫자가 아님
          name: 123, // 문자열이 아님
        },
      ],
    };

    const dto = plainToInstance(NestedCategoryDTO, invalidChildrenData);
    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors.map((error) => error.property)).toEqual(expect.arrayContaining(["children"]));
  });

  it("중첩된 children 필드가 유효할 경우 유효성 검사가 통과", async () => {
    const validChildrenData = {
      ...validData,
      children: [
        {
          id: 2,
          name: "서브 카테고리",
          slug: "sub-category-slug",
          description: "서브 카테고리 설명",
          level: 3,
          image_url: "http://example.com/sub-image.jpg",
        },
      ],
    };

    const dto = plainToInstance(NestedCategoryDTO, validChildrenData);
    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("children 필드가 빈 배열일 경우 유효성 검사가 통과", async () => {
    const dataWithEmptyChildren = { ...validData, children: [] };

    const dto = plainToInstance(NestedCategoryDTO, dataWithEmptyChildren);
    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("children 필드는 선택적", async () => {
    const dataWithoutChildren = { ...validData, children: undefined };

    const dto = plainToInstance(NestedCategoryDTO, dataWithoutChildren);
    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});
