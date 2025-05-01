import { Category } from "src/domain/entities";

type NestedCategoryDTO = Category & {
  children?: NestedCategoryDTO[];
};

export default NestedCategoryDTO;
