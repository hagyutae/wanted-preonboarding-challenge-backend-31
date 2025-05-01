import { applyDecorators, Type } from "@nestjs/common";
import { ApiExtraModels, ApiResponse, getSchemaPath } from "@nestjs/swagger";

import { ResponseDTO } from "../dto";

export function ApiStandardResponse<TModel extends Type<any>>(
  description: string,
  model?: TModel,
  status = 200,
) {
  if (!model) {
    return applyDecorators(
      ApiExtraModels(ResponseDTO),
      ApiResponse({
        status,
        description,
        type: ResponseDTO,
      }),
    );
  }

  return applyDecorators(
    ApiExtraModels(ResponseDTO, model),
    ApiResponse({
      status,
      description,
      schema: {
        allOf: [
          { $ref: getSchemaPath(ResponseDTO) },
          {
            properties: {
              data: { $ref: getSchemaPath(model) },
            },
          },
        ],
      },
    }),
  );
}

export function ApiCreatedResponse<TModel extends Type<any>>(description: string, model?: TModel) {
  return ApiStandardResponse<TModel>(description, model, 201);
}
