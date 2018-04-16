import {User} from "../User";

export class ExtCourse{
  id: number;
  name: string;
  description: string;
  price: number;
  rating: number;
  publisher: User;
  link: string;
  source: string;

  constructor(id: number, name: string, description: string, price: number, rating: number, publisher: User, link: string, source: string) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.rating = rating;
    this.publisher = publisher;
    this.link = link;
    this.source = source;
  }
}
