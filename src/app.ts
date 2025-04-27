import express from 'express';
import cors from 'cors';
import morgan from 'morgan';
import { config } from './config/index';
import { prisma } from './lib/prisma';

/**
 * 라우트 임포트
 */
import productRoutes from './routes/product.routes';
import categoryRoutes from './routes/category.routes';
import reviewRoutes from './routes/review.routes';
import mainRoutes from './routes/main.routes';

const app = express();

/**
 * 미들웨어 설정
 */
app.use(cors());
app.use(morgan('dev')); // logging
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

/**
 * API 라우트 설정
 */
app.use('/api/products', productRoutes);
app.use('/api/categories', categoryRoutes);
app.use('/api/reviews', reviewRoutes);
app.use('/api/main', mainRoutes);

/**
 * 기본 라우트
 */
app.get('/', (req, res) => {
  res.send('이커머스 API 서버가 실행 중입니다.');
});

/**
 * 서버 시작
 */
const PORT = config.port || 3000;

if (import.meta.env.PROD) {
  app.listen(PORT, () => {
    console.log(`서버가 포트 ${PORT}에서 실행 중입니다.`);
  });
}

/**
 * DB 연결 초기화 및 확인
 */
async function checkDatabaseConnection() {
  try {
    await prisma.$connect();
    console.log('데이터베이스 연결 성공.');
  } catch (err) {
    console.error('데이터베이스 연결 실패:', err);
    process.exit(1);
  }
}

checkDatabaseConnection();

/**
 * 애플리케이션 종료 시 Prisma 연결 해제
 */
process.on('beforeExit', async () => {
  await prisma.$disconnect();
});

export { app };
