import { PrismaClient } from '@prisma/client';

/**
 * 전역 변수 타입 선언 - 개발 환경에서의 핫 리로딩 지원을 위함
 */
// eslint-disable-next-line no-var
declare global {
  var prisma: PrismaClient | undefined;
}

/**
 * Prisma 클라이언트 인스턴스 - 개발 환경에서는 핫 리로딩마다
 * 새로운 PrismaClient 인스턴스가 생성되는 것을 방지하기 위해
 * 전역 변수로 관리합니다.
 */
export const prisma = global.prisma || new PrismaClient();

if (process.env.NODE_ENV !== 'production') {
  global.prisma = prisma;
}
