import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";
import ProductBodyDTO, { AdditionalInfo } from "./ProductBody.dto";

describe("ProductBodyDTO", () => {
  const validData = {
    name: "슈퍼 편안한 소파",
    slug: "super-comfortable-sofa",
    short_description: "최고급 소재로 만든 편안한 소파",
    full_description: "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
    seller_id: 1,
    brand_id: 2,
    status: "ACTIVE",
    detail: {
      weight: 25.5,
      dimensions: {
        width: 200,
        height: 85,
        depth: 90,
      },
      materials: "가죽, 목재, 폼",
      country_of_origin: "대한민국",
      warranty_info: "2년 품질 보증",
      care_instructions: "마른 천으로 표면을 닦아주세요",
      additional_info: {
        assembly_required: true,
        assembly_time: "30분",
      },
    },
    price: {
      base_price: 599000,
      sale_price: 499000,
      cost_price: 350000,
      currency: "KRW",
      tax_rate: 10,
    },
    categories: [
      { category_id: 5, is_primary: true },
      { category_id: 6, is_primary: false },
    ],
    option_groups: [
      {
        name: "색상",
        display_order: 1,
        options: [
          { name: "브라운", additional_price: 0, sku: "SOFA-BRN", stock: 10, display_order: 1 },
        ],
      },
    ],
    images: [
      {
        url: "https://example.com/images/sofa1.jpg",
        alt_text: "브라운 소파 정면",
        is_primary: true,
        display_order: 1,
        option_id: null,
      },
    ],
    tags: [1, 4, 7],
  };

  it("유효한 데이터로 유효성 검사가 성공", async () => {
    const dto = plainToInstance(ProductBodyDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("잘못된 데이터 타입으로 유효성 검사가 실패", async () => {
    const invalidData = { ...validData, seller_id: "invalid" };
    const dto = plainToInstance(ProductBodyDTO, invalidData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("seller_id");
  });

  it("중첩된 객체의 유효성 검사가 성공", async () => {
    const dto = plainToInstance(ProductBodyDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("중첩된 객체의 유효성 검사가 실패", async () => {
    const invalidData = {
      ...validData,
      detail: { ...validData.detail, dimensions: { width: "invalid", height: 85, depth: 90 } },
    };
    const dto = plainToInstance(ProductBodyDTO, invalidData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("detail");
  });

  it("배열 필드의 유효성 검사가 성공", async () => {
    const dto = plainToInstance(ProductBodyDTO, validData);

    const errors = await validate(dto);

    expect(errors.length).toBe(0);
  });

  it("배열 필드의 유효성 검사가 실패", async () => {
    const invalidData = { ...validData, tags: ["invalid"] };
    const dto = plainToInstance(ProductBodyDTO, invalidData);

    const errors = await validate(dto);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors[0].property).toBe("tags");
  });

  describe("AdditionalInfo", () => {
    it("유효한 데이터로 유효성 검사가 성공", async () => {
      const validAdditionalInfo = {
        assembly_required: true,
        assembly_time: "30분",
      };

      const dto = plainToInstance(AdditionalInfo, validAdditionalInfo);

      const errors = await validate(dto);

      expect(errors.length).toBe(0);
    });

    it("assembly_time이 잘못된 형식으로 유효성 검사가 실패", async () => {
      const invalidAdditionalInfo = {
        assembly_required: true,
        assembly_time: "30분1시간간",
      };

      const dto = plainToInstance(AdditionalInfo, invalidAdditionalInfo);

      const errors = await validate(dto);

      expect(errors.length).toBeGreaterThan(0);
      expect(errors[0].property).toBe("assembly_time");
    });

    it("currency 필드가 유효한 형식일 때 유효성 검사가 성공", async () => {
      const dto = plainToInstance(ProductBodyDTO, validData);

      const errors = await validate(dto);

      expect(errors.length).toBe(0);
    });

    it("currency 필드가 잘못된 형식일 때 유효성 검사가 실패", async () => {
      const invalidData = { ...validData, price: { ...validData.price, currency: "US" } };
      const dto = plainToInstance(ProductBodyDTO, invalidData);

      const errors = await validate(dto);

      expect(errors.length).toBeGreaterThan(0);
      expect(errors[0].property).toBe("price");
    });

    it("currency 필드가 숫자일 때 유효성 검사가 실패", async () => {
      const invalidData = { ...validData, price: { ...validData.price, currency: 123 } };
      const dto = plainToInstance(ProductBodyDTO, invalidData);

      const errors = await validate(dto);

      expect(errors.length).toBeGreaterThan(0);
      expect(errors[0].property).toBe("price");
    });
  });
});
