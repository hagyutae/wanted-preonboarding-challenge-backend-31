export default interface GetQueryDTO {
  page?: number;
  perPage?: number;
  sort?: string;
  status?: string;
  minPrice?: number;
  maxPrice?: number;
  category?: number;
  seller?: number;
  brand?: number;
  inStock?: boolean;
  search?: string;
}
