import { Module } from '@nestjs/common';
import { ReviewsService } from './reviews.service';
import { ReviewsController } from './reviews.controller';
import { DrizzleModule } from '~/database/drizzle.module';
import { ReviewsRepository } from './reviews.repository';

@Module({
  imports: [DrizzleModule],
  controllers: [ReviewsController],
  providers: [ReviewsService, ReviewsRepository],
  exports: [ReviewsService],
})
export class ReviewsModule {}
