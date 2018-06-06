import {Course} from "./Course";
import {User} from "./User";

export class Program {
  id: number;
  name: string;
  description: string;
  publisher: User;


  constructor(id: number, name: string, description: string, publisher: User) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.publisher = publisher;
  }

  static createEmpty() : Program{
    return new Program(undefined,'','', undefined);
  }
}
