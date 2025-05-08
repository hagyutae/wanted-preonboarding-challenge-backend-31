import { createSuccessResponseSchema } from '../utils/response-schema.util';
import { z } from 'zod';

export const DeleteResponseDtoSchema = createSuccessResponseSchema(null);
export type DeleteResponseDto = z.infer<typeof DeleteResponseDtoSchema>;
