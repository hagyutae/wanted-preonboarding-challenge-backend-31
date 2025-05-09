import { plainToInstance } from "class-transformer";
import { validate } from "class-validator";

import ErrorCode, { HttpStatusToErrorCodeMap } from "@libs/constants/ErrorCode";
import ErrorDTO, { ErrorDetails } from "./Error.dto";

describe("ErrorDTO", () => {
  const validateDTO = async (dto: Partial<ErrorDTO>) => {
    const instance = plainToInstance(ErrorDTO, dto);

    const errors = await validate(instance);

    return errors.map((error) => error.constraints);
  };

  it("유효한 DTO는 검증을 통과", async () => {
    const dto: Partial<ErrorDTO> = {
      success: false,
      error: {
        code: ErrorCode.INVALID_INPUT,
        message: "Invalid input data",
        details: { field: "name", issue: "is required" },
      },
    };

    const errors = await validateDTO(dto);

    expect(errors).toHaveLength(0);
  });

  it("success 필드가 없으면 검증 실패", async () => {
    const dto: Partial<ErrorDTO> = {
      error: {
        code: ErrorCode.INVALID_INPUT,
        message: "Invalid input data",
      },
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("error.code가 유효하지 않은 값이면 검증 실패", async () => {
    const dto: Partial<ErrorDTO> = {
      success: false,
      error: {
        code: "INVALID_CODE" as ErrorCode,
        message: "Invalid input data",
      },
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("error.details가 객체가 아니면 검증 실패", async () => {
    const dto: Partial<ErrorDTO> = {
      success: false,
      error: {
        code: ErrorCode.INVALID_INPUT,
        message: "Invalid input data",
        details: "Invalid details" as unknown as ErrorDetails,
      },
    };

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("모든 필드가 올바르게 설정되지 않으면 검증 실패", async () => {
    const dto: Partial<ErrorDTO> = {};

    const errors = await validateDTO(dto);

    expect(errors).not.toHaveLength(0);
  });

  it("error.code가 ErrorCode의 ENUM 값이어야 함", async () => {
    const validDto: Partial<ErrorDTO> = {
      success: false,
      error: {
        code: ErrorCode.INVALID_INPUT,
        message: "Valid error message",
      },
    };

    const invalidDto: Partial<ErrorDTO> = {
      success: false,
      error: {
        code: "INVALID_ENUM" as ErrorCode,
        message: "Invalid error message",
      },
    };

    const validErrors = await validateDTO(validDto);
    const invalidErrors = await validateDTO(invalidDto);

    expect(validErrors).toHaveLength(0);
    expect(invalidErrors).not.toHaveLength(0);
  });

  it("HttpStatusToErrorCodeMap에 정의되지 않은 상태 코드가 포함되지 않음", () => {
    const invalidStatusCodes = Object.keys(HttpStatusToErrorCodeMap).filter(
      (statusCode) => ![400, 401, 403, 404, 409, 500].includes(Number(statusCode)),
    );

    expect(invalidStatusCodes).toHaveLength(0);
  });

  it("HttpStatusToErrorCodeMap의 값이 ErrorCode에 정의된 값과 일치", () => {
    const errorCodes = Object.values(ErrorCode);
    const mappedErrorCodes = Object.values(HttpStatusToErrorCodeMap);

    mappedErrorCodes.forEach((code) => {
      expect(errorCodes).toContain(code);
    });
  });
});
