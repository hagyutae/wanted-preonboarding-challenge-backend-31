import { Controller, Get } from '@nestjs/common';
import { MainService } from './main.service';
import { ApiOperation } from '@nestjs/swagger';

@Controller('main')
export class MainController {
  constructor(private readonly mainService: MainService) {}

  @Get()
  @ApiOperation({ summary: '메인 페이지 조회' })
  async getMain() {
    return this.mainService.getMain();
  }
}
