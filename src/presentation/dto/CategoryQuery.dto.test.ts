import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import CategoryQueryDTO from "./CategoryQuery.dto";

describe("CategoryQueryDTO", () => {
  const validData = {
    page: 1,
    perPage: 10,
    sort: "created_at:desc",
    includeSubcategories: true,
  };

  const validateDTO = async (dto: Partial<CategoryQueryDTO>) => {
    const instance = plainToInstance(CategoryQueryDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<CategoryQueryDTO> = {
      page: 1,
      perPage: 10,
      sort: "created_at:desc",
      includeSubcategories: true,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("모든 필드가 선택 사항이므로 빈 객체도 검증을 통과", async () => {
    const dto: Partial<CategoryQueryDTO> = {};

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("sort 필드의 유효성 검사가 성공 (올바른 형식)", async () => {
    const validSortData = { ...validData };
    const dto = plainToInstance(CategoryQueryDTO, validSortData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("sort 필드의 유효성 검사가 실패 (잘못된 형식)", async () => {
    const invalidSortData = { ...validData, sort: "invalid_sort_format" };
    const dto = plainToInstance(CategoryQueryDTO, invalidSortData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("sort");
    expect(errors[0].constraints?.matches).toContain("sort 형식은 field:asc|desc이어야 합니다.");
  });

  it("sort 필드의 유효성 검사가 성공 (기본값 사용)", async () => {
    const dto = plainToInstance(
      CategoryQueryDTO,
      { ...validData, sort: undefined },
      { exposeDefaultValues: true },
    );

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
    expect(dto.sort).toBe("created_at:desc");
  });
});
