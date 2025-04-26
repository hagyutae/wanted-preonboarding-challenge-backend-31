import User from "./User";

export default class Seller extends User {
  constructor(
    public id: string,
    public name: string,
    public description: string,
    public logo_url: string,
    public rating: string,
  ) {
    super(id);
  }
}
