export default class Category {
  constructor(
    public id: string,
    public parent: Category | null,
    public name: string,
    public slug: string,
    public description: string,
    public level: string,
    public image_url: string,
  ) {}
}
