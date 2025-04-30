import { FilterDTO } from "src/application/dto";
import { FiltersByCategoryDTO, ProductQueryDTO, ReviewQueryDTO } from "../dto";

const field_mapping = {
  includeSubcategories: "has_sub",
  inStock: "in_stock",
  minPrice: "min_price",
  maxPrice: "max_price",
};

export default function to_FilterDTO(
  query: ProductQueryDTO | FiltersByCategoryDTO | ReviewQueryDTO,
): FilterDTO {
  return Object.entries(query).reduce((acc, [field, value]) => {
    const mapped_field = field_mapping[field] || field;
    acc[mapped_field] = value;
    return acc;
  }, {} as FilterDTO);
}
