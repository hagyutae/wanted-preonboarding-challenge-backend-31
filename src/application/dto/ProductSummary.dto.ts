type ProductSummaryDTO = {
  id: number;
  name: string;
  slug: string;
  short_description: string;
  base_price: number;
  sale_price: number;
  currency: string;
  primary_image: { url: string; alt_text: string };
  brand: { id: number; name: string };
  seller: { id: number; name: string };
  status: string;
  in_stock: boolean;
  rating: number;
  review_count: number;
  created_at: Date;
  updated_at?: Date;
};

export default ProductSummaryDTO;
