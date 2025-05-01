export default interface IRepository<T> {
  save(param: T): Promise<T>;
  saves(param: T[]): Promise<T[]>;
  find_by_id(id: number): Promise<T | null>;
  find_by_filters(filters: any): Promise<T[]>;
  update(param: T, id: number): Promise<T | void>;
  delete(id: number): Promise<void>;
}
