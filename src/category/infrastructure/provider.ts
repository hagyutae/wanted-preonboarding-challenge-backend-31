import { CategoryRepository } from "./repositories";

export default [{ provide: "ICategoryRepository", useClass: CategoryRepository }];
