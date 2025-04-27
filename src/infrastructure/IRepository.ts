export default interface IRepository<T> {
  create(data: T): Promise<T>;
  findAll(filters: any): Promise<T[]>;
  findById(id: string): Promise<T | null>;
  update(id: string, data: T): Promise<T>;
  delete(id: string): Promise<void>;
}
