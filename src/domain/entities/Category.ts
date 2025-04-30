export default class Category {
  constructor(
    public name: string,
    public slug: string,
    public description: string,
    public level: string,
    public image_url: string,
    public parent: Category | null,
    public id?: number,
  ) {}
}
