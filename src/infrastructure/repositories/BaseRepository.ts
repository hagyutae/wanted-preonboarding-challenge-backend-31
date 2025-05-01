import { EntityManager } from "typeorm";

import IRepository from "src/domain/repositories/IRepository";

export default abstract class BaseRepository<T> implements IRepository<T> {
  constructor(protected readonly entity_manager: EntityManager) {}

  with_transaction<M extends BaseRepository<T>>(this: M, manager: EntityManager): M {
    const Repository_Class = this.constructor as { new (manager: EntityManager): M };
    return new Repository_Class(manager);
  }

  save(param: T): Promise<T> {
    throw new Error("Method not implemented.");
  }
  saves(param: T[]): Promise<T[]> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<T | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<T[]> {
    throw new Error("Method not implemented.");
  }
  update(param: T, id?: number): Promise<void | T> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
