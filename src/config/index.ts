import dotenv from 'dotenv';

// .env 파일 로드
dotenv.config();

interface Config {
  port: number;
  nodeEnv: string;
  db: {
    url: string;
  };
}

export const config: Config = {
  port: parseInt(process.env.PORT || '3000', 10),
  nodeEnv: process.env.NODE_ENV || 'development',
  db: {
    url: process.env.DATABASE_URL || 'postgresql://postgres:postgres@localhost:5432/ecommerce',
  },
}; 