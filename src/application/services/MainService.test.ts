import { Test, TestingModule } from "@nestjs/testing";
import { EntityManager } from "typeorm";

import { CategoryEntity, ProductEntity } from "src/infrastructure/entities";
import MainService from "./MainService";

describe("MainService", () => {
  let service: MainService;
  let mockEntityManager: EntityManager;
  let mockQueryBuilder: any;

  beforeEach(async () => {
    mockQueryBuilder = {
      innerJoinAndSelect: jest.fn().mockReturnThis(),
      leftJoin: jest.fn().mockReturnThis(),
      leftJoinAndSelect: jest.fn().mockReturnThis(),
      select: jest.fn().mockReturnThis(),
      addSelect: jest.fn().mockReturnThis(),
      where: jest.fn().mockReturnThis(),
      andWhere: jest.fn().mockReturnThis(),
      groupBy: jest.fn().mockReturnThis(),
      addGroupBy: jest.fn().mockReturnThis(),
      orderBy: jest.fn().mockReturnThis(),
      skip: jest.fn().mockReturnThis(),
      take: jest.fn().mockReturnThis(),
      limit: jest.fn().mockReturnThis(),
      getMany: jest.fn(),
      getRawMany: jest.fn(),
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        MainService,
        {
          provide: EntityManager,
          useValue: {
            findOne: jest.fn(),
            create: jest.fn(),
            save: jest.fn(),
            update: jest.fn(),
            delete: jest.fn(),
            getRepository: jest.fn().mockReturnValue({
              createQueryBuilder: jest.fn().mockReturnValue(mockQueryBuilder),
            }),
          },
        },
      ],
    }).compile();

    service = module.get<MainService>(MainService);
    mockEntityManager = module.get<EntityManager>(EntityManager);
  });

  describe("getNewProducts", () => {
    it("새 상품 목록 조회 성공", async () => {
      const mockProducts = [{ id: 1, name: "새 상품" }] as ProductEntity[];
      mockEntityManager.find = jest.fn().mockResolvedValue(mockProducts);

      const result = await service.getNewProducts();

      expect(result).toEqual(mockProducts);
    });
  });

  describe("getPopularProducts", () => {
    it("인기 상품 목록 조회 성공", async () => {
      const data = [{ id: 1, name: "인기 상품" }] as CategoryEntity[];
      mockQueryBuilder.getRawMany = jest.fn().mockReturnValue(data);

      const result = await service.getPopularProducts();

      expect(result).toEqual(data);
    });
  });

  describe("getFeaturedCategories", () => {
    it("추천 카테고리 목록 조회 성공", async () => {
      const mockCategories = [{ id: 1, name: "추천 카테고리" }] as CategoryEntity[];
      mockEntityManager.find = jest.fn().mockResolvedValue(mockCategories);

      const result = await service.getFeaturedCategories();

      expect(result).toEqual(mockCategories);
    });
  });
});
