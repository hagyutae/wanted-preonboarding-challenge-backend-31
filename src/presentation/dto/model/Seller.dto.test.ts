import getValidateDTO from "src/__test-utils__/getValidateDTO";

import SellerDTO from "./Seller.dto";

describe("SellerDTO", () => {
  const validateDTO = getValidateDTO(SellerDTO);

  const validData: Partial<SellerDTO> = {
    id: 1,
    name: "홈퍼니처",
    description: "최고의 가구 전문 판매점",
    logo_url: "https://example.com/sellers/homefurniture.png",
    rating: 4.8,
    contact_email: "contact@homefurniture.com",
    contact_phone: "02-1234-5678",
  };

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const errors = await validateDTO(validData);

    expect(errors).toHaveLength(0);
  });

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(6);
    expect(errors).toContain("name");
    expect(errors).toContain("description");
    expect(errors).toContain("logo_url");
    expect(errors).toContain("rating");
    expect(errors).toContain("contact_email");
    expect(errors).toContain("contact_phone");
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

  it("rating 필드가 음수인 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      rating: -1,
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("rating");
  });

  it("contact_email 필드가 이메일 형식이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      contact_email: "invalid-email",
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("contact_email");
  });

  it("contact_phone 필드가 전화번호 형식이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      contact_phone: "invalid-phone",
    };

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("contact_phone");
  });
});
