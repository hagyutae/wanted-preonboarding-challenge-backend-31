export default class Brand {
  constructor(
    public name: string,
    public slug: string,
    public description?: string,
    public logo_url?: string,
    public website?: string,
    public id?: number,
  ) {}
}
