import { PrismaClient } from '@prisma/client';

// PrismaClient 인스턴스를 글로벌로 선언하여 중복 인스턴스 생성 방지
const globalForPrisma = globalThis as unknown as {
  prisma: PrismaClient | undefined;
};

// 개발 환경에서는 핫 리로딩마다 새로운 PrismaClient 인스턴스가 생성되는 것을 방지
export const prisma = globalForPrisma.prisma ?? new PrismaClient({
  log: ['query', 'info', 'warn', 'error'],
});

if (import.meta.env.DEV) globalForPrisma.prisma = prisma; 