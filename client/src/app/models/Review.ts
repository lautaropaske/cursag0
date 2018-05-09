import {Course} from "./Course";
import {User} from "./User";

export class Review {
  id: number;

  textContent: string;

  rating: number;

  publisher: User;

  reviewed: Course;


  constructor(id: number, textContent: string, rating: number, publisher: User, reviewed: Course) {
    this.id = id;
    this.textContent = textContent;
    this.rating = rating;
    this.publisher = publisher;
    this.reviewed = reviewed;
  }
}
