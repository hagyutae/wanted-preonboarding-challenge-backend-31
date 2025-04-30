import { TypeOrmModuleOptions } from "@nestjs/typeorm";

export default {
  useFactory: (): TypeOrmModuleOptions => {
    const { DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD, DB_DATABASE } = process.env;

    return {
      type: "postgres",
      host: DB_HOST,
      port: Number(DB_PORT),
      username: DB_USERNAME,
      password: DB_PASSWORD,
      database: DB_DATABASE,
      entities: [__dirname + "/**/*.entity.js", __dirname + "/**/*.view.js"],
      synchronize: true, // 개발
    };
  },
};
