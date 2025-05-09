import getValidateDTO from "__test-utils__/getValidateDTO";

import { OptionParamDTO, ParamDTO } from "./Param.dto";

describe("ParamDTO", () => {
  const validateDTO = getValidateDTO(ParamDTO);

  it("유효한 데이터로 유효성 검증을 통과", async () => {
    const dto: Partial<ParamDTO> = {
      id: 123,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("id가 누락된 경우 검증 실패", async () => {
    const dto: Partial<ParamDTO> = {};

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("id가 정수가 아닌 경우 검증 실패", async () => {
    const dto: Partial<ParamDTO> = {
      id: "123" as any,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("id");
  });
});

describe("OptionParamDTO", () => {
  const validateDTO = getValidateDTO(OptionParamDTO);

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<OptionParamDTO> = {
      id: 123,
      optionId: 456,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("id가 누락된 경우 검증 실패", async () => {
    const dto: Partial<OptionParamDTO> = {
      optionId: 456,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("id");
  });

  it("optionId가 누락된 경우 검증 실패", async () => {
    const dto: Partial<OptionParamDTO> = {
      id: 123,
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(1);
    expect(errors).toContain("optionId");
  });
});
