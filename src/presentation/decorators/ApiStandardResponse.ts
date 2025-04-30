import { applyDecorators } from "@nestjs/common";
import { ApiResponse } from "@nestjs/swagger";

import { ResponseDTO } from "../dto";

export function ApiStandardResponse(description: string) {
  return applyDecorators(ApiResponse({ status: 200, description, type: ResponseDTO }));
}

export function ApiCreatedResponse(description: string) {
  return applyDecorators(ApiResponse({ status: 201, description, type: ResponseDTO }));
}
