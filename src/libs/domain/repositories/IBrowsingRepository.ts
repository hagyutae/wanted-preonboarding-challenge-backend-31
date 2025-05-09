export default interface IBrowsingRepository {
  get_new_products(page: number, per_page: number);
  get_popular_products();
  get_featured_categories();
}
