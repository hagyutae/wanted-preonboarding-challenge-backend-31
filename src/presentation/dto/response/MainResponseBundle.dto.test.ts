import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import MainResponseBundleDTO from "./MainResponseBundle.dto";

describe("MainResponseBundle", () => {
  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const validData = {
      new_products: [],
      popular_products: [],
      featured_categories: [],
    };
    const dto = plainToInstance(MainResponseBundleDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});
