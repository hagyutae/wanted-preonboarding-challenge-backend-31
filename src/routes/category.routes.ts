import express, { Request, Response } from 'express';
import { prisma } from '../lib/prisma';
// import { categoryController } from '../controllers/category.controller.js';

const router = express.Router();

// 카테고리 목록 조회 (계층 구조 포함)
router.get('/', async (req: Request, res: Response) => {
  try {
    // 최상위 카테고리 (level 1) 조회 및 하위 카테고리 포함
    const categories = await prisma.category.findMany({
      where: {
        level: 1
      },
      include: {
        children: {
          include: {
            children: true
          }
        }
      },
      orderBy: {
        id: 'asc'
      }
    });

    res.json({
      status: 'success',
      data: categories
    });
  } catch (error) {
    console.error('카테고리 목록 조회 오류:', error);
    res.status(500).json({ message: '카테고리 목록을 가져오는 중 오류가 발생했습니다.' });
  }
});

// 특정 카테고리의 상품 목록 조회
router.get('/:id/products', async (req: Request, res: Response) => {
  try {
    const categoryId = parseInt(req.params.id);
    
    // 해당 카테고리에 속하는 상품 조회
    const products = await prisma.product.findMany({
      where: {
        categories: {
          some: {
            categoryId
          }
        }
      },
      include: {
        brand: true,
        images: {
          where: { isPrimary: true },
          take: 1
        },
        price: true
      },
      take: 10 // 임시로 10개만 가져옴
    });
    
    res.json({
      status: 'success',
      data: products,
      pagination: {
        total: products.length,
        page: 1,
        limit: 10
      }
    });
  } catch (error) {
    console.error('카테고리별 상품 목록 조회 오류:', error);
    res.status(500).json({ message: '카테고리별 상품 목록을 가져오는 중 오류가 발생했습니다.' });
  }
});

export default router; 