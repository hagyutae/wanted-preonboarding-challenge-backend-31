import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import TagDTO from "./Tag.dto";

describe("TagDTO", () => {
  const validateDTO = async (dto: Partial<TagDTO>) => {
    const instance = plainToInstance(TagDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<TagDTO> = {
    id: 1,
    name: "편안함",
    slug: "comfort",
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(3);
    expect(errors).toContain("id");
    expect(errors).toContain("name");
    expect(errors).toContain("slug");
  });

  it("id 필드가 정수가 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      id: "not-an-integer" as unknown as number,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("id");
  });
});
