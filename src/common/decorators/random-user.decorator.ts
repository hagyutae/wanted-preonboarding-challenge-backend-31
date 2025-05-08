import { createParamDecorator, ExecutionContext } from '@nestjs/common';

export const RandomUser = createParamDecorator(
  (data: unknown, ctx: ExecutionContext) => {
    // 1부터 10까지의 랜덤한 숫자 생성
    const randomUserId = Math.floor(Math.random() * 10) + 1;
    return randomUserId;
  },
);
