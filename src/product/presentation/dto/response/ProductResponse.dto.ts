import { PickType } from "@nestjs/swagger";

import { ProductCatalogDTO } from "@libs/common/dto";

export default class ProductResponseDTO extends PickType(ProductCatalogDTO, [
  "id",
  "name",
  "slug",
  "created_at",
  "updated_at",
] as const) {}
