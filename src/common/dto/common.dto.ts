import { z } from 'zod';
import { createSuccessResponseSchema } from '../utils/response-schema.util';

export const DeleteResponseDtoSchema = createSuccessResponseSchema(null);
export type DeleteResponseDto = z.infer<typeof DeleteResponseDtoSchema>;
