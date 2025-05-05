import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ProductsModule } from './products/products.module';
import { PrismaModule } from './prisma/prisma.module';
import { CategoriesModule } from './categories/categories.module';
import { MainModule } from './main/main.module';

@Module({
  imports: [PrismaModule, ProductsModule, CategoriesModule, MainModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
