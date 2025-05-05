import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ProductResponseDTO from "./ProductResponse.dto";

describe("ProductResponseDTO", () => {
  const validateDTO = async (dto: Partial<ProductResponseDTO>) => {
    const instance = plainToInstance(ProductResponseDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<ProductResponseDTO> = {
      id: 1,
      name: "소파",
      slug: "super-comfortable-sofa",
      created_at: new Date("2025-04-14T09:30:00Z"),
      updated_at: new Date("2025-04-14T09:30:00Z"),
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("created_at이 잘못된 날짜 형식인 경우 검증 실패", async () => {
    const dto: Partial<ProductResponseDTO> = {
      id: 1,
      name: "소파",
      slug: "super-comfortable-sofa",
      created_at: "invalid-date" as any,
      updated_at: new Date("2025-04-14T09:30:00Z"),
    };

    const errors = await validateDTO(dto);

    expect(errors.length).toBeGreaterThan(0);
  });

  it("updated_at이 잘못된 날짜 형식인 경우 검증 실패", async () => {
    const dto: Partial<ProductResponseDTO> = {
      id: 1,
      name: "소파",
      slug: "super-comfortable-sofa",
      created_at: new Date("2025-04-14T09:30:00Z"),
      updated_at: "invalid-date" as any,
    };

    const errors = await validateDTO(dto);

    expect(errors.length).toBeGreaterThan(0);
  });
});
