import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";
import ResponseDTO, {
  ProductResponseBundle,
  CategoryResponseBundle,
  ReviewResponseBundle,
  MainResponseBundle,
} from "./Response.dto";

describe("ResponseDTO", () => {
  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const validData = { success: true, data: {} };
    const dto = plainToInstance(ResponseDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("message 필드가 선택적으로 유효성 검사가 성공", async () => {
    const validData = { success: true, data: {}, message: "요청이 성공적으로 처리되었습니다." };
    const dto = plainToInstance(ResponseDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});

describe("ProductResponseBundle", () => {
  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const validData = {
      items: [],
      pagination: {},
    };
    const dto = plainToInstance(ProductResponseBundle, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});

describe("CategoryResponseBundle", () => {
  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const validData = {
      category: {},
      items: [],
      pagination: {},
    };
    const dto = plainToInstance(CategoryResponseBundle, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});

describe("ReviewResponseBundle", () => {
  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const validData = {
      items: [],
      summary: {},
      pagination: {},
    };
    const dto = plainToInstance(ReviewResponseBundle, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});

describe("MainResponseBundle", () => {
  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const validData = {
      new_products: [],
      popular_products: [],
      featured_categories: [],
    };
    const dto = plainToInstance(MainResponseBundle, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});
