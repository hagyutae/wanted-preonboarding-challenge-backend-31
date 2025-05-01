import { EntityManager } from "typeorm";

import IRepository from "../../domain/repositories/IRepository";

export default abstract class BaseRepository<T, U> implements IRepository<T, U> {
  constructor(protected readonly entity_manager: EntityManager) {}

  save(param: T): Promise<U> {
    throw new Error("Method not implemented.");
  }
  saves(param: T[]): Promise<U[]> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<U | null> {
    throw new Error("Method not implemented.");
  }
  find_by_filters(filters: any): Promise<U[]> {
    throw new Error("Method not implemented.");
  }
  update(param: T, id: number): Promise<void | U> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
