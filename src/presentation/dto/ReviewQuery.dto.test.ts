import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ReviewQueryDTO from "./ReviewQuery.dto";

describe("ReviewQueryDTO", () => {
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
});
