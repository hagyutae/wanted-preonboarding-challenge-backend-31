import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import BrandDTO from "./Brand.dto";

describe("BrandDTO", () => {
  const validateDTO = async (dto: Partial<BrandDTO>) => {
    const instance = plainToInstance(BrandDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<BrandDTO> = {
    id: 1,
    name: "편안가구",
    description: "편안함을 추구하는 가구 브랜드",
    logo_url: "https://example.com/brands/comfortfurniture.png",
    website: "https://comfortfurniture.com",
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = { ...validData };
    delete invalidData.name;

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("name");
  });

  it("logo_url 필드가 url이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      logo_url: "invalid-url",
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("logo_url");
  });

  it("website 필드가 url이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      website: "invalid-url",
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("website");
  });
});
