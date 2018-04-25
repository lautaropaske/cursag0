import {User} from "./User";
import {Unit} from "./Unit";

export class LocalCourse{
  id: number;
  name: string;
  description: string;
  price: number;
  rating: number;
  publisher: User;
  units: Unit[];

  constructor(id: number, name: string, description: string, price: number, rating: number, publisher: User) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.rating = rating;
    this.publisher = publisher;
    this.units = undefined;
  }

  static create_empty() : LocalCourse{
    return new LocalCourse(undefined,'','',undefined, undefined,User.create_empty());
  }
}
