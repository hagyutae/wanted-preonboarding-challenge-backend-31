import getValidateDTO from "__test-utils__/getValidateDTO";

import { PaginationSummaryDTO } from "@libs/common/dto";
import ProductSummaryDTO from "@product/presentation/dto/response/ProductSummary.dto";
import CategoryResponseBundleDTO, { CategoryResponseDTO } from "./CategoryResponseBundle.dto";

describe("CategoryResponseBundleDTO", () => {
  const validateDTO = getValidateDTO(CategoryResponseBundleDTO);

  const validData: Partial<CategoryResponseBundleDTO> = {
    category: { name: "카테고리 정보" } as CategoryResponseDTO,
    // items: [{ name: "상품 요약" }] as ProductSummaryDTO[],
    pagination: {
      total_items: 100,
      total_pages: 10,
      current_page: 1,
      per_page: 10,
    } as PaginationSummaryDTO,
  };

  it("category 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, category: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(1);
    expect(errors).toContain("category");
  });

  it("items 필드가 배열이 아닌 경우 검증 실패", async () => {
    const invalidData = { ...validData, items: "not_an_array" as unknown as ProductSummaryDTO[] };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(1);
    expect(errors).toContain("items");
  });

  it("pagination 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, pagination: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(1);
    expect(errors).toContain("pagination");
  });
});
