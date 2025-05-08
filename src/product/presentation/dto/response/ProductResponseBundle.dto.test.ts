import getValidateDTO from "__test-utils__/getValidateDTO";

import { PaginationSummaryDTO, ProductSummaryDTO } from "@libs/common/dto";
import ProductResponseBundleDTO from "./ProductResponseBundle.dto";

describe("ProductResponseBundleDTO", () => {
  const validateDTO = getValidateDTO(ProductResponseBundleDTO);

  const validData: Partial<ProductResponseBundleDTO> = {
    items: [{ name: "상품 요약" }] as ProductSummaryDTO[],
    pagination: {
      total_items: 100,
      total_pages: 10,
      current_page: 1,
      per_page: 10,
    } as PaginationSummaryDTO,
  };

  it("items 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, items: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("items");
  });

  it("items 필드가 배열이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, items: "not_an_array" as unknown as ProductSummaryDTO[] };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("items");
  });

  it("pagination 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, pagination: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("pagination");
  });
});
