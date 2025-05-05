import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ProductQueryDTO from "./ProductQuery.dto";

describe("ProductQueryDTO", () => {
  const validateDTO = async (dto: Partial<ProductQueryDTO>) => {
    const instance = plainToInstance(ProductQueryDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData = {
    page: 1,
    perPage: 10,
    sort: "created_at:desc",
    status: "ACTIVE",
    minPrice: 10000,
    maxPrice: 100000,
    category: [5],
    seller: 1,
    brand: 2,
    inStock: true,
    search: "소파",
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
      ProductQueryDTO,
      { ...validData, sort: undefined },
      { exposeDefaultValues: true },
    );

    const errors = await validate(defaultSortData);

    expect(errors.length).toBe(0);
    expect(defaultSortData.sort).toBe("created_at:desc");
  });

  it("status 필드가 허용되지 않은 값일 경우 검증 실패", async () => {
    const invalidData = { ...validData, status: "INVALID_STATUS" };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("status");
  });

  it("category 필드가 숫자 배열이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, category: ["not_a_number"] as unknown as number[] };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("category");
  });

  it("inStock 필드가 boolean이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, inStock: "not_a_boolean" as unknown as boolean };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("inStock");
  });

  it("minPrice와 maxPrice가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, minPrice: -1, maxPrice: -1 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(2);
    expect(errors).toContain("minPrice");
    expect(errors).toContain("maxPrice");
  });

  it("page와 perPage가 1보다 작은 경우 검증 실패", async () => {
    const invalidData = { ...validData, page: 0, perPage: 0 };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(2);
    expect(errors).toContain("page");
    expect(errors).toContain("perPage");
  });
});
