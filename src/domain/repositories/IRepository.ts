export default interface IRepository<T, U> {
  save(param: T): Promise<U>;
  saves(param: T[]): Promise<U[]>;
  find_by_id(id: number): Promise<U | null>;
  find_by_filters(filters: any): Promise<U[]>;
  update(param: T, id: number): Promise<U | void>;
  delete(id: number): Promise<void>;
}
