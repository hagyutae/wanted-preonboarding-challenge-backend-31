import { BrowsingRepository } from "./repositories";

export default [{ provide: "IBrowsingRepository", useClass: BrowsingRepository }];
