import Brand from "./Brand";
import Product_Category from "./Product_Category";
import Product_Detail from "./Product_Detail";
import Product_Image from "./Product_Image";
import Product_Option_Group from "./Product_Option_Group";
import Product_Price from "./Product_Price";
import Seller from "./Seller";
import Tag from "./Tag";

export default class Product_Catalog {
  constructor(
    public id: number,
    public name: string,
    public slug: string,
    public short_description: string,
    public full_description: string,
    public status: string,
    public created_at: Date,
    public updated_at: Date,

    public seller: Seller,

    public brand: Brand,

    public detail: Product_Detail,

    public price: Product_Price & { discount_percentage: number },

    public categories: Product_Category[],

    public option_groups: Product_Option_Group[],

    public images: Product_Image[],

    public tags: Tag[],

    public rating: {
      average: number;
      count: number;
      distribution: {
        "5": number;
        "4": number;
        "3": number;
        "2": number;
        "1": number;
      };
    },
  ) {}
}
