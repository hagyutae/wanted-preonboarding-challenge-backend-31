import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ReviewQueryDTO from "./ReviewQuery.dto";

describe("ReviewQueryDTO", () => {
  const validData = {
    page: 1,
    perPage: 10,
    sort: "created_at:desc",
    rating: 4,
  };

  const validateDTO = async (dto: Partial<ReviewQueryDTO>) => {
    const instance = plainToInstance(ReviewQueryDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<ReviewQueryDTO> = {
      page: 1,
      perPage: 10,
      sort: "created_at:desc",
      rating: 4,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("유효하지 않은 페이지 번호는 검증 실패", async () => {
    const dto: Partial<ReviewQueryDTO> = {
      page: -1,
    };

    const errors = await validateDTO(dto);

    expect(errors.length).toBeGreaterThan(0);
  });

  it("유효하지 않은 평점은 검증 실패", async () => {
    const dto: Partial<ReviewQueryDTO> = {
      rating: 6,
    };

    const errors = await validateDTO(dto);

    expect(errors.length).toBeGreaterThan(0);
  });

  it("모든 필드가 비어 있어도 검증을 통과", async () => {
    const dto: Partial<ReviewQueryDTO> = {};

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("sort 필드의 유효성 검사가 성공 (올바른 형식)", async () => {
    const validSortData = { ...validData, sort: "name:asc,price:desc" };
    const dto = plainToInstance(ReviewQueryDTO, validSortData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("sort 필드의 유효성 검사가 실패 (잘못된 형식)", async () => {
    const invalidSortData = { ...validData, sort: "invalid_sort_format" };
    const dto = plainToInstance(ReviewQueryDTO, invalidSortData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("sort");
    expect(errors[0].constraints?.matches).toContain("sort 형식은 field:asc|desc 이어야 합니다.");
  });

  it("sort 필드의 유효성 검사가 실패 (빈 문자열)", async () => {
    const invalidSortData = { ...validData, sort: "" };
    const dto = plainToInstance(ReviewQueryDTO, invalidSortData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("sort");
  });

  it("sort 필드의 유효성 검사가 성공 (기본값 사용)", async () => {
    const dto = plainToInstance(
      ReviewQueryDTO,
      { ...validData, sort: undefined },
      { exposeDefaultValues: true },
    );

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
    expect(dto.sort).toBe("created_at:desc");
  });
});
