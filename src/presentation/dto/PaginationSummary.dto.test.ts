import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import PaginationSummaryDTO from "./PaginationSummary.dto";

describe("PaginationSummaryDTO", () => {
  const validateDTO = async (dto: Partial<PaginationSummaryDTO>) => {
    const instance = plainToInstance(PaginationSummaryDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<PaginationSummaryDTO> = {
      total_items: 50,
      total_pages: 5,
      current_page: 1,
      per_page: 10,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });
});
