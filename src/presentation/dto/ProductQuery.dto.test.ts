import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";
import ProductQueryDTO from "./ProductQuery.dto";

describe("ProductQueryDTO", () => {
  const validData = {
    page: 1,
    perPage: 10,
    sort: "created_at:desc",
    status: "ACTIVE",
    minPrice: 10000,
    maxPrice: 100000,
    category: "5,6",
    seller: 1,
    brand: 2,
    inStock: true,
    search: "소파",
  };

  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const dto = plainToInstance(ProductQueryDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("필수 필드가 누락되었을 때 유효성 검사가 성공 (모든 필드가 선택적)", async () => {
    const dto = plainToInstance(ProductQueryDTO, {});

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("잘못된 데이터 타입으로 유효성 검사가 실패", async () => {
    const invalidData = { ...validData, page: "invalid" };
    const dto = plainToInstance(ProductQueryDTO, invalidData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("page");
  });

  it("배열 필드(category)의 유효성 검사가 성공", async () => {
    const dto = plainToInstance(ProductQueryDTO, { ...validData, category: "5,6" });

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("배열 필드(category)의 유효성 검사가 실패", async () => {
    const invalidData = { ...validData, category: "invalid" };
    const dto = plainToInstance(ProductQueryDTO, invalidData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("category");
  });

  it("boolean 필드(inStock)의 유효성 검사가 성공", async () => {
    const dto = plainToInstance(ProductQueryDTO, { ...validData, inStock: true });

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("boolean 필드(inStock)의 유효성 검사가 실패", async () => {
    const invalidData = { ...validData, inStock: "invalid" };
    const dto = plainToInstance(ProductQueryDTO, invalidData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("inStock");
  });

  it("숫자 필드(minPrice, maxPrice)의 유효성 검사가 성공", async () => {
    const dto = plainToInstance(ProductQueryDTO, { ...validData, minPrice: 5000, maxPrice: 20000 });

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("숫자 필드(minPrice, maxPrice)의 유효성 검사가 실패", async () => {
    const invalidData = { ...validData, minPrice: "invalid", maxPrice: "invalid" };
    const dto = plainToInstance(ProductQueryDTO, invalidData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("minPrice");
  });

  it("sort 필드의 유효성 검사가 성공 (올바른 형식)", async () => {
    const validSortData = { ...validData, sort: "name:asc,price:desc" };
    const dto = plainToInstance(ProductQueryDTO, validSortData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("sort 필드의 유효성 검사가 실패 (잘못된 형식)", async () => {
    const invalidSortData = { ...validData, sort: "invalid_sort_format" };
    const dto = plainToInstance(ProductQueryDTO, invalidSortData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("sort");
    expect(errors[0].constraints?.matches).toContain(
      "sort 형식은 field:asc|desc (복수는 콤마로 구분) 이어야 합니다.",
    );
  });

  it("sort 필드의 유효성 검사가 실패 (빈 문자열)", async () => {
    const invalidSortData = { ...validData, sort: "" };
    const dto = plainToInstance(ProductQueryDTO, invalidSortData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("sort");
  });

  it("sort 필드의 유효성 검사가 성공 (기본값 사용)", async () => {
    const dto = plainToInstance(
      ProductQueryDTO,
      { ...validData, sort: undefined },
      { exposeDefaultValues: true },
    );

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
    expect(dto.sort).toBe("created_at:desc");
  });
});
