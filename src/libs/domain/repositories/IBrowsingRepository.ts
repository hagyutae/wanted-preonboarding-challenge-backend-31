export default interface IBrowsingRepository {
  find_by_filters(filters: any);
  find_by_id(id: number);
  get_featured_categories();
}
