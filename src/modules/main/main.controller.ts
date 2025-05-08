import { Controller, Get } from '@nestjs/common';
import { MainService } from './main.service';
import { GetMainPageResponseDto } from './dto/main.dto';
import { createSuccessResponse } from '~/common/utils/response.util';

@Controller('main')
export class MainController {
  constructor(private readonly mainService: MainService) {}

  @Get()
  async getMainPage(): Promise<GetMainPageResponseDto> {
    const data = await this.mainService.getMainPage();
    return createSuccessResponse(
      data,
      '메인 페이지 상품 목록을 성공적으로 조회했습니다.',
    );
  }
}
