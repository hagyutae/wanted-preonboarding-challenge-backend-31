import { EntityManager } from "typeorm";

export const mockEntityManager = {
  create: jest.fn(),
  save: jest.fn(),
  find: jest.fn(),
  findOne: jest.fn(),
  update: jest.fn(),
  delete: jest.fn(),
  getRepository: jest.fn().mockReturnThis(),
  createQueryBuilder: jest.fn().mockReturnValue({
    subQuery: jest.fn().mockReturnThis(),
    select: jest.fn().mockReturnThis(),
    addSelect: jest.fn().mockReturnThis(),
    from: jest.fn().mockReturnThis(),
    leftJoin: jest.fn().mockReturnThis(),
    leftJoinAndSelect: jest.fn().mockReturnThis(),
    innerJoin: jest.fn().mockReturnThis(),
    innerJoinAndSelect: jest.fn().mockReturnThis(),
    where: jest.fn().mockReturnThis(),
    andWhere: jest.fn().mockReturnThis(),
    orderBy: jest.fn().mockReturnThis(),
    offset: jest.fn().mockReturnThis(),
    limit: jest.fn().mockReturnThis(),
    setParameter: jest.fn().mockReturnThis(),
    getQuery: jest.fn().mockReturnValue("mockInnerQuery"),
    getMany: jest.fn(),
  }),
} as unknown as jest.Mocked<EntityManager>;
