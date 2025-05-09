export interface PaginationParams {
  page: number;
  perPage: number;
  sort: string;
}

export interface PaginationResult<T> {
  data: T[];
  pagination: {
    total: number;
    page: number;
    perPage: number;
    totalPages: number;
  };
}

export function parsePaginationParams(query: Record<string, unknown>): PaginationParams {
  const page = typeof query.page === 'string' ? parseInt(query.page, 10) : 1;
  const perPage = typeof query.perPage === 'string' ? parseInt(query.perPage, 10) : 10;
  const sort = typeof query.sort === 'string' ? query.sort : 'created_at:desc';

  return {
    page,
    perPage,
    sort,
  };
}

export function getPaginationResult<T>(
  data: T[],
  total: number,
  page: number,
  perPage: number
): PaginationResult<T> {
  return {
    data,
    pagination: {
      total,
      page,
      perPage,
      totalPages: Math.ceil(total / perPage),
    },
  };
} 