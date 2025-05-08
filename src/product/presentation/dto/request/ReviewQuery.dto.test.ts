import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import getValidateDTO from "__test-utils__/getValidateDTO";

import ReviewQueryDTO from "./ReviewQuery.dto";

describe("ReviewQueryDTO", () => {
  const validateDTO = getValidateDTO(ReviewQueryDTO);

  const validData: ReviewQueryDTO = {
    page: 1,
    perPage: 10,
    sort: "created_at:desc",
    rating: 4,
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

  it("sort 필드가 기본값으로 유효성 검증 통과", async () => {
    const defaultSortData = plainToInstance(
      ReviewQueryDTO,
      { ...validData, sort: undefined },
      { exposeDefaultValues: true },
    );

    const errors = await validate(defaultSortData);

    expect(errors.length).toBe(0);
    expect(defaultSortData.sort).toBe("created_at:desc");
  });

  it("rating 필드가 범위를 벗어난 경우 검증 실패", async () => {
    const invalidData = { ...validData, rating: 6 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("rating");
  });

  it("rating 필드가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, rating: -1 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("rating");
  });

  it("page 필드가 1보다 작은 경우 검증 실패", async () => {
    const invalidData = { ...validData, page: 0 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("page");
  });

  it("perPage 필드가 1보다 작은 경우 검증 실패", async () => {
    const invalidData = { ...validData, perPage: 0 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("perPage");
  });
});
