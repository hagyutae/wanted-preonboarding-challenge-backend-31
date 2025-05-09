import express from 'express';
import { mainController } from '../controllers/main.controller';

const router = express.Router();

/**
 * @route GET /api/main
 * @desc 메인 페이지용 데이터 조회 (신규 상품, 인기 상품, 주요 카테고리 정보)
 * @access Public
 */
router.get('/', mainController.getMainPageData);

export default router;
