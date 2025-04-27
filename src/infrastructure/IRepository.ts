export default interface IRepository<T> {
  create(data: T): Promise<T>;
  findAll(filters: any): Promise<T[]>;
  findById(id: number): Promise<T | null>;
  update(id: number, data: T): Promise<T>;
  delete(id: number): Promise<void>;
}
