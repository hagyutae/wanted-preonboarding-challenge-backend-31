import { Test, TestingModule } from "@nestjs/testing";
import { TypeOrmModule } from "@nestjs/typeorm";
import { PostgreSqlContainer, StartedPostgreSqlContainer } from "@testcontainers/postgresql";
import { DataSource } from "typeorm";

import { repository_providers } from "../infrastructure/provider";
import * as entities from "../infrastructure/entities";

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
          entities,
          synchronize: true,
        }),
      }),
      TypeOrmModule.forFeature(Object.values(entities)),
    ],
    providers: [...repository_providers],
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
