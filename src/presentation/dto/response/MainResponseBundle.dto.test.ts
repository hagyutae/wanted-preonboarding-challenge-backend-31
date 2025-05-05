import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import MainResponseBundleDTO, { FeaturedCategoryDTO } from "./MainResponseBundle.dto";
import ProductSummaryDTO from "../response/ProductSummary.dto";

describe("MainResponseBundleDTO", () => {
  const validateDTO = async (dto: Partial<MainResponseBundleDTO>) => {
    const instance = plainToInstance(MainResponseBundleDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<MainResponseBundleDTO> = {
    new_products: [{ name: "신상품" }] as ProductSummaryDTO[],
    popular_products: [{ name: "인기 상품" }] as ProductSummaryDTO[],
    featured_categories: [{ name: "추천 카테고리" }] as FeaturedCategoryDTO[],
  };

  it("new_products 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, new_products: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("new_products");
  });

  it("popular_products 필드가 배열이 아닌 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      popular_products: "not_an_array" as unknown as ProductSummaryDTO[],
    };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("popular_products");
  });

  it("featured_categories 필드가 없을 경우 검증 실패", async () => {
    const invalidData = { ...validData, featured_categories: undefined };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("featured_categories");
  });

  it("featured_categories 필드의 product_count가 음수일 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      featured_categories: [
        {
          id: 1,
          name: "Category 1",
          slug: "category-1",
          image_url: "http://example.com/image.jpg",
          product_count: -1,
        },
      ] as FeaturedCategoryDTO[],
    };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("featured_categories");
  });
});
