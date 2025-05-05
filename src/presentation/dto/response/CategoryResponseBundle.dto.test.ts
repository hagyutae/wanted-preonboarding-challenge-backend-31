import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import CategoryResponseBundleDTO from "./CategoryResponseBundle.dto";

describe("CategoryResponseBundle", () => {
  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const validData = {
      category: {},
      items: [],
      pagination: {},
    };
    const dto = plainToInstance(CategoryResponseBundleDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });
});
