import { Module } from '@nestjs/common';
import { MainController } from './main.controller';
import { MainService } from './main.service';
import { MainRepository } from './main.repository';
import { DrizzleModule } from '~/database/drizzle.module';

@Module({
  imports: [DrizzleModule],
  controllers: [MainController],
  providers: [MainService, MainRepository],
})
export class MainModule {}
