import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ProductSummaryDTO from "./ProductSummary.dto";

describe("ProductSummaryDTO", () => {
  const validateDTO = async (dto: Partial<ProductSummaryDTO>) => {
    const instance = plainToInstance(ProductSummaryDTO, dto);
    const errors = await validate(instance);
    return errors.map((error) => error.property);
  };

  const validData: Partial<ProductSummaryDTO> = {
    id: 1,
    name: "슈퍼 편안한 소파",
    slug: "super-comfortable-sofa",
    short_description: "최고급 소재로 만든 편안한 소파",
    status: "ACTIVE",
    created_at: new Date("2025-04-10T09:30:00Z"),
    base_price: 599000,
    sale_price: 499000,
    currency: "KRW",
    primary_image: { url: "https://example.com/images/sofa1.jpg", alt_text: "브라운 소파 정면" },
    brand: { id: 1, name: "편안가구" },
    seller: { id: 3, name: "홈퍼니처" },
    in_stock: true,
    rating: 4.7,
    review_count: 128,
  };

  it("유효한 데이터로 성공적으로 검증되어야 한다", async () => {
    const errors = await validateDTO(validData);

    console.log("ProductSummary 유효한 데이터 검증 결과:", errors);
    expect(errors.length).toBe(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(15);
    expect(errors).toContain("id");
    expect(errors).toContain("name");
    expect(errors).toContain("slug");
    expect(errors).toContain("short_description");
    expect(errors).toContain("status");
    expect(errors).toContain("created_at");
    expect(errors).toContain("base_price");
    expect(errors).toContain("sale_price");
    expect(errors).toContain("currency");
    expect(errors).toContain("primary_image");
    expect(errors).toContain("brand");
    expect(errors).toContain("seller");
    expect(errors).toContain("in_stock");
    expect(errors).toContain("rating");
    expect(errors).toContain("review_count");
  });

  it("base_price가 음수일 경우 검증에 실패해야 한다", async () => {
    const invalidData = { ...validData, base_price: -100 };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("base_price");
  });

  it("currency가 3자리 대문자 코드가 아닐 경우 검증에 실패해야 한다", async () => {
    const invalidData = { ...validData, currency: "usd" };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("currency");
  });

  it("in_stock이 boolean이 아닐 경우 검증에 실패해야 한다", async () => {
    const invalidData = { ...validData, in_stock: "yes" as unknown as boolean };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("in_stock");
  });

  it("rating이 범위를 벗어날 경우 검증에 실패해야 한다", async () => {
    const invalidData = { ...validData, rating: 6 };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("rating");
  });

  it("review_count가 음수일 경우 검증에 실패해야 한다", async () => {
    const invalidData = { ...validData, review_count: -1 };

    const errors = await validateDTO(invalidData);

    expect(errors).toContain("review_count");
  });
});
