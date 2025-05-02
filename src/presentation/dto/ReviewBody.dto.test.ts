import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ReviewBodyDTO from "./ReviewBody.dto";

describe("ReviewBodyDTO", () => {
  const validateDTO = async (dto: Partial<ReviewBodyDTO>) => {
    const instance = plainToInstance(ReviewBodyDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<ReviewBodyDTO> = {
      rating: 5,
      title: "완벽한 소파입니다!",
      content: "배송도 빠르고 품질도 매우 좋습니다. 색상도 사진과 동일하고 조립도 쉬웠어요.",
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });
});
