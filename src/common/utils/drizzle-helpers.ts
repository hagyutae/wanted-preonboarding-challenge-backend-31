import { asc, desc } from 'drizzle-orm';
import { products as productsSchema } from '~/database/schema';

type EntityType = 'product' | 'category';
type SortField = 'created_at';
type SortOrder = 'asc' | 'desc';
type SortString = `${SortField}:${SortOrder}`;

const fieldMaps: Record<EntityType, Record<SortField, any>> = {
  product: {
    created_at: productsSchema.createdAt,
  },
  category: {
    created_at: productsSchema.createdAt,
  },
};

export const getOrderBy = (entityType: EntityType, sort: SortString) => {
  const fieldMap = fieldMaps[entityType];
  const [field, order] = sort.split(':') as [SortField, SortOrder];

  if (!fieldMap[field]) {
    return desc(productsSchema.createdAt);
  }
  return order === 'desc' ? desc(fieldMap[field]) : asc(fieldMap[field]);
};
