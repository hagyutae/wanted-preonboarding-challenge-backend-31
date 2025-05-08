import { mockEntityManager } from "./src/__mocks__/entityManagerMock";
import * as mockRepositoryMocks from "./src/__mocks__/repositoryMock";
import { stop_test_module } from "./src/__test-utils__/test-module";

(global as any).mockEntityManager = mockEntityManager;

Object.entries(mockRepositoryMocks).forEach(([key, value]) => {
  (global as any)[key] = value;
});

afterAll(async () => {
  await stop_test_module();
});
