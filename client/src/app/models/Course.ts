import {User} from "./User";
import {Unit} from "./Unit";

export class Course{
  id: number;

  name: string;

  description: string;

  price: number;

  rating: number;

  publisher: User;

  //External
  link: string;

  source: string;

  //Local
  units: Unit[];


}
