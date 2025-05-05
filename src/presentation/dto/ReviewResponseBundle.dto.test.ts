import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ReviewResponseBundle from "./ReviewResponseBundle.dto";

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
