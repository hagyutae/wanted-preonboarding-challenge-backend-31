import { ReviewRepository } from "./repositories";

export default [{ provide: "IReviewRepository", useClass: ReviewRepository }];
