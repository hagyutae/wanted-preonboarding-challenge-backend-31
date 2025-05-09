import { Test, TestingModule } from "@nestjs/testing";
import { TypeOrmModule } from "@nestjs/typeorm";
import { PostgreSqlContainer, StartedPostgreSqlContainer } from "@testcontainers/postgresql";
import { DataSource } from "typeorm";

import * as product_entities from "@product/infrastructure/entities";
import product_repository_providers from "@product/infrastructure/provider";
import * as category_entities from "@category/infrastructure/entities";
import category_repository_providers from "@category/infrastructure/provider";
import * as review_entities from "@review/infrastructure/entities";
import review_repository_providers from "@review/infrastructure/provider";
import * as views from "@browsing/infrastructure/views";

let container: StartedPostgreSqlContainer;
let test_module: TestingModule;

export async function get_module() {
  if (test_module) {
    return test_module;
  }
  container = await new PostgreSqlContainer()
    .withDatabase("testdb")
    .withUsername("testuser")
    .withPassword("testpassword")
    .start();

  test_module = await Test.createTestingModule({
    imports: [
      TypeOrmModule.forRootAsync({
        useFactory: () => ({
          type: "postgres",
          host: container.getHost(),
          port: container.getPort(),
          username: container.getUsername(),
          password: container.getPassword(),
          database: container.getDatabase(),
          entities: [
            ...Object.values(product_entities),
            ...Object.values(category_entities),
            ...Object.values(review_entities),
            ...Object.values(views),
          ],
          synchronize: true,
        }),
      }),
      TypeOrmModule.forFeature([
        ...Object.values(product_entities),
        ...Object.values(category_entities),
        ...Object.values(review_entities),
        ...Object.values(views),
      ]),
    ],
    providers: [
      ...product_repository_providers,
      ...category_repository_providers,
      ...review_repository_providers,
    ],
  }).compile();

  return test_module;
}

export async function stop_test_module() {
  if (test_module) {
    const dataSource = test_module.get<DataSource>(DataSource);
    await dataSource.destroy();
  }
  if (container) {
    await container.stop();
  }
}
