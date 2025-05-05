import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import CategoryQueryDTO from "./CategoryQuery.dto";

describe("CategoryQueryDTO", () => {
  const validateDTO = async (dto: Partial<CategoryQueryDTO>) => {
    const instance = plainToInstance(CategoryQueryDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData = {
    page: 1,
    perPage: 10,
    sort: "created_at:desc",
    includeSubcategories: true,
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("모든 필드가 선택 사항이므로 빈 객체도 검증 통과", async () => {
    const errors = await validateDTO({});

    expect(errors).toHaveLength(0);
  });

  it("sort 필드가 잘못된 형식일 경우 검증 실패", async () => {
    const invalidData = { ...validData, sort: "invalid_sort_format" };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("sort");
  });

  it("sort 필드가 기본갑으로 유효성 검증 통과", async () => {
    const defaultSortData = plainToInstance(
      CategoryQueryDTO,
      { ...validData, sort: undefined },
      { exposeDefaultValues: true },
    );

    const errors = await validate(defaultSortData);

    expect(errors.length).toBe(0);
    expect(defaultSortData.sort).toBe("created_at:desc");
  });

  it("includeSubcategories 필드가 boolean이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      includeSubcategories: "not_a_boolean" as unknown as boolean,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("includeSubcategories");
  });
});
