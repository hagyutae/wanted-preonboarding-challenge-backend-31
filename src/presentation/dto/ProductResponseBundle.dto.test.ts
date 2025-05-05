import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ProductResponseBundle from "./ProductResponseBundle.dto";

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
