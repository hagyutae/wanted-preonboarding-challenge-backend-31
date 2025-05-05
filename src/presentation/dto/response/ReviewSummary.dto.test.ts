import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ReviewSummaryDTO from "./ReviewSummary.dto";

describe("ReviewSummaryDTO", () => {
  const validateDTO = async (dto: Partial<ReviewSummaryDTO>) => {
    const instance = plainToInstance(ReviewSummaryDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.property);
  };

  const validData: Partial<ReviewSummaryDTO> = {
    average_rating: 4.5,
    total_count: 150,
    distribution: {
      1: 10,
      2: 20,
      3: 30,
      4: 25,
      5: 15,
    },
  };

  it("필수 필드가 누락된 경우 검증 실패", async () => {
    const invalidData = {};

    const errors = await validateDTO(invalidData);

    expect(errors).toHaveLength(3);
    expect(errors).toContain("average_rating");
    expect(errors).toContain("total_count");
    expect(errors).toContain("distribution");
  });

  it("average_rating 필드가 범위를 벗어난 경우 검증 실패", async () => {
    const invalidData = { ...validData, average_rating: 6 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("average_rating");
  });

  it("total_count 필드가 음수일 경우 검증 실패", async () => {
    const invalidData = { ...validData, total_count: -10 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("total_count");
  });

  it("distribution 필드가 객체가 아닐 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      distribution: "not_an_object" as any,
    };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("distribution");
  });

  it("distribution 필드가 정해진 키 (1-5)가 아닐 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      distribution: {
        1: 10,
        6: 5,
      } as any,
    };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("distribution");
  });

  it("distribution 필드의 값이 음수일 경우 검증 실패", async () => {
    const invalidData = {
      ...validData,
      distribution: {
        1: -5,
        2: 20,
        3: 30,
        4: 25,
        5: 15,
      },
    };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("distribution");
  });

  it("average_rating 필드가 숫자가 아닐 경우 검증 실패", async () => {
    const invalidData = { ...validData, average_rating: "not_a_number" as unknown as number };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("average_rating");
  });

  it("total_count 필드가 정수가 아닐 경우 검증 실패", async () => {
    const invalidData = { ...validData, total_count: 10.5 };

    const errors = await validateDTO(invalidData);

    expect(errors.length).toBeGreaterThan(0);
    expect(errors).toContain("total_count");
  });
});
