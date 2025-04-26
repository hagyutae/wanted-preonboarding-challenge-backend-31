import Product from "./Product";

export default class Review {
  constructor(
    public id: string,
    public product: Product,
    public user_id: string,
    public rating: number,
    public title: string,
    public created_at: Date,
    public updated_at: Date,
    public verified_purchase: boolean,
    public helpful_votes: number
  ) {}
}
