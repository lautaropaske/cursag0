import {Course} from "./Course";
import {User} from "./User";
import {ExtCourse} from "./ExtCourse";

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

  static create_empty() : Review{
    return new Review(undefined,'',undefined,undefined, undefined);
  }
}
