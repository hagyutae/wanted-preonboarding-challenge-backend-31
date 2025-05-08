import { z } from 'zod';

export const UserSchema = z.object({
  id: z.number(),
  name: z.string(),
  email: z.string(),
  avatarUrl: z.string(),
  createdAt: z.date(),
});
export type User = z.infer<typeof UserSchema>;
