import {Course} from "./Course";
import {User} from "./User";

export class Program {
  id: number;
  name: string;
  description: string;
  rating: number;
  publisher: User;


  constructor(id: number, name: string, description: string, rating: number, publisher: User) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.rating = rating;
    this.publisher = publisher;
  }

  static create_empty() : Program{
    return new Program(undefined,'',undefined,undefined, undefined);
  }
}
