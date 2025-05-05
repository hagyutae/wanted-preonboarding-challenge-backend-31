import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ProductSummaryDTO from "../response/ProductSummary.dto";
import CategoryResponseBundleDTO, { CategoryResponseDTO } from "./CategoryResponseBundle.dto";
import PaginationSummaryDTO from "./PaginationSummary.dto";

describe("CategoryResponseBundleDTO", () => {
  const validateDTO = async (dto: Partial<CategoryResponseBundleDTO>) => {
    const instance = plainToInstance(CategoryResponseBundleDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<CategoryResponseBundleDTO> = {
    category: { name: "카테고리 정보" } as CategoryResponseDTO,
    items: [{ name: "상품 요약" }] as ProductSummaryDTO[],
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
