import User from "./User";

export default class Review {
  constructor(
    public rating: number,
    public user?: User,
    public title?: string,
    public content?: string,
    public verified_purchase?: boolean,
    public helpful_votes?: number,
    public id?: number,
    public product_id?: number,
    public created_at?: Date,
    public updated_at?: Date,
  ) {}
}
