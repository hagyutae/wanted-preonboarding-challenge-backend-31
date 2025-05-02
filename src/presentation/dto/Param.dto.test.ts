import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import { ParamDTO, OptionParamDTO } from "./Param.dto";

describe("ParamDTO", () => {
  const validateDTO = async (dto: Partial<ParamDTO>) => {
    const instance = plainToInstance(ParamDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
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

    expect(errors).not.toHaveLength(0);
  });
});

describe("OptionParamDTO", () => {
  const validateDTO = async (dto: Partial<OptionParamDTO>) => {
    const instance = plainToInstance(OptionParamDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

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

    expect(errors).not.toHaveLength(0);
  });

  it("optionId가 누락된 경우 검증 실패", async () => {
    const dto: Partial<OptionParamDTO> = {
      id: 123,
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("id가 정수가 아닌 경우 검증 실패", async () => {
    const dto: Partial<OptionParamDTO> = {
      id: "123" as any,
      optionId: 456,
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("optionId가 정수가 아닌 경우 검증 실패", async () => {
    const dto: Partial<OptionParamDTO> = {
      id: 123,
      optionId: "456" as any,
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });
});
