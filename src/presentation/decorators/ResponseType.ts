import { SetMetadata } from "@nestjs/common";

import { RESPONSE_DTO_KEY } from "src/utility/ResponseInterceptor";

export default (dto: any) => SetMetadata(RESPONSE_DTO_KEY, dto);
