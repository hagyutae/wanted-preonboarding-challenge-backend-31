import express, { Request, Response } from 'express';
import { prisma } from '../lib/prisma';
// import { productController } from '../controllers/product.controller.js';

const router = express.Router();

// 상품 목록 조회 (검색, 필터링 포함)
router.get('/', async (req: Request, res: Response) => {
  try {
    const products = await prisma.product.findMany({
      include: {
        brand: true,
        seller: true,
        images: {
          where: { isPrimary: true },
          take: 1,
        },
      },
      take: 10, // 임시로 10개만 가져옴
    });
    
    res.json({
      status: 'success',
      data: products,
      pagination: {
        total: products.length,
        page: 1,
        limit: 10,
      }
    });
  } catch (error) {
    console.error('상품 목록 조회 오류:', error);
    res.status(500).json({ message: '상품 목록을 가져오는 중 오류가 발생했습니다.' });
  }
});

// 상품 상세 조회
router.get('/:id', async (req: Request, res: Response) => {
  try {
    const productId = parseInt(req.params.id);
    
    const product = await prisma.product.findUnique({
      where: { id: productId },
      include: {
        brand: true,
        seller: true,
        detail: true,
        price: true,
        images: true,
        optionGroups: {
          include: {
            options: true,
          },
        },
        reviews: {
          include: {
            user: true,
          },
          take: 5,
          orderBy: {
            createdAt: 'desc',
          },
        },
      },
    });
    
    if (!product) {
      return res.status(404).json({ message: '상품을 찾을 수 없습니다.' });
    }
    
    res.json({
      status: 'success',
      data: product,
    });
  } catch (error) {
    console.error('상품 상세 조회 오류:', error);
    res.status(500).json({ message: '상품 상세 정보를 가져오는 중 오류가 발생했습니다.' });
  }
});

// 상품 등록
router.post('/', (req: Request, res: Response) => {
  res.json({ message: '상품 등록 API가 구현 예정입니다.' });
});

// 상품 수정
router.put('/:id', (req: Request, res: Response) => {
  res.json({ message: `상품 ID ${req.params.id} 수정 API가 구현 예정입니다.` });
});

// 상품 삭제
router.delete('/:id', (req: Request, res: Response) => {
  res.json({ message: `상품 ID ${req.params.id} 삭제 API가 구현 예정입니다.` });
});

// 상품 옵션 추가
router.post('/:id/options', (req: Request, res: Response) => {
  res.json({ message: `상품 ID ${req.params.id}의 옵션 추가 API가 구현 예정입니다.` });
});

// 상품 옵션 수정
router.put('/:id/options/:optionId', (req: Request, res: Response) => {
  res.json({ 
    message: `상품 ID ${req.params.id}의 옵션 ID ${req.params.optionId} 수정 API가 구현 예정입니다.` 
  });
});

// 상품 옵션 삭제
router.delete('/:id/options/:optionId', (req: Request, res: Response) => {
  res.json({ 
    message: `상품 ID ${req.params.id}의 옵션 ID ${req.params.optionId} 삭제 API가 구현 예정입니다.` 
  });
});

// 상품 이미지 추가
router.post('/:id/images', (req: Request, res: Response) => {
  res.json({ message: `상품 ID ${req.params.id}의 이미지 추가 API가 구현 예정입니다.` });
});

export default router; 