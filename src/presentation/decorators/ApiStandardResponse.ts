import { applyDecorators, Type } from "@nestjs/common";
import { ApiExtraModels, ApiResponse, getSchemaPath } from "@nestjs/swagger";

import extractDTOExample from "src/utility/extractDTOExample";
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

  const example = extractDTOExample(model);

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
              data: {
                allOf: [
                  {
                    $ref: getSchemaPath(model),
                  },
                  {
                    example,
                  },
                ],
              },
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
