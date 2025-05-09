export default interface IBrowsingRepository {
  find_by_filters(filters: any);
  get_featured_categories();
}
