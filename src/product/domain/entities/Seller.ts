import User from "../../../review/domain/entities/User";

export default class Seller extends User {
  constructor(
    public name: string,
    public description: string,
    public logo_url: string,
    public rating: number,
    public contact_email: string,
    public contact_phone: string,
    public id?: number,
  ) {
    super(id);
  }
}
