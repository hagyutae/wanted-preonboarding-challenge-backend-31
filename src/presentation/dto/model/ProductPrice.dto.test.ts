import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ProductPriceDTO from "./ProductPrice.dto";

describe("ProductPriceDTO", () => {
  const validateDTO = async (dto: Partial<ProductPriceDTO>) => {
    const instance = plainToInstance(ProductPriceDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<ProductPriceDTO> = {
    base_price: 599000,
    sale_price: 499000,
    cost_price: 350000,
    currency: "KRW",
    tax_rate: 10,
    discount_percentage: 17,
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(4);
    expect(errors).toContain("base_price");
    expect(errors).toContain("sale_price");
    expect(errors).toContain("currency");
    expect(errors).toContain("tax_rate");
  });

  it("currency 필드가 3자리 대문자가 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      currency: "invalid",
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("currency");
  });

  it("tax_rate 필드가 음수인 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      tax_rate: -5,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("tax_rate");
  });

  it("discount_percentage 필드가 100을 초과하는 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      discount_percentage: 150,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("discount_percentage");
  });

  it("sale_price 필드가 음수인 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      sale_price: -100,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("sale_price");
  });

  it("cost_price 필드가 음수인 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      cost_price: -200,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("cost_price");
  });
});
