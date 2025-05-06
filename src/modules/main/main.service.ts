import { Injectable } from '@nestjs/common';
import { MainRepository } from './main.repository';
import { MainPageResponse } from './dto/main.dto';

@Injectable()
export class MainService {
  constructor(private readonly mainRepository: MainRepository) {}

  async getMainPage(): Promise<MainPageResponse> {
    const [newProducts, popularProducts, featuredCategories] =
      await Promise.all([
        this.mainRepository.getNewProducts(),
        this.mainRepository.getPopularProducts(),
        this.mainRepository.getFeaturedCategories(),
      ]);

    return {
      newProducts,
      popularProducts,
      featuredCategories,
    };
  }
}
