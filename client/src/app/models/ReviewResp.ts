import {User} from "./User";
import {Review} from "./Review";
import {Course} from "./Course";

export class ReviewResp {
  id: number;
  textContent: string;
  rating: number;
  publisher: User;

  constructor(id: number, textContent: string, rating: number, publisher: User) {
    this.id = id;
    this.textContent = textContent;
    this.rating = rating;
    this.publisher = publisher;
  }


  // static create_empty() : ReviewResp{
  //   return new ReviewResp(undefined,'',undefined,undefined);
  // }
}
