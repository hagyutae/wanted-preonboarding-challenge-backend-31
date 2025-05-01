import { mockEntityManager } from "src/__mocks__/entityManagerMock";
import * as mockRepositoryMocks from "src/__mocks__/repositoryMock";

(global as any).mockEntityManager = mockEntityManager;

Object.entries(mockRepositoryMocks).forEach(([key, value]) => {
  (global as any)[key] = value;
});
