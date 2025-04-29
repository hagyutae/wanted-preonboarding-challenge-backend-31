import { TypeOrmModule } from "@nestjs/typeorm";

export default TypeOrmModule.forRoot({
  type: "postgres",
  host: "localhost",
  port: 5432,
  username: "user",
  password: "pswd",
  database: "db",
  entities: [__dirname + "/**/*.entity.js", __dirname + "/**/*.view.js"],
  synchronize: true, // 개발
});
