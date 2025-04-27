import User from "./User";

export default class Seller extends User {
  constructor(
    public name: string,
    public description: string,
    public logo_url: string,
    public rating: number,
    public id?: number,
  ) {
    super(id);
  }
}
