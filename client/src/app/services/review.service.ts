import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Review} from "../models/Review";

const URL ="http://localhost:8080/review";

@Injectable()
export class ReviewService {

  constructor(private http: HttpClient) {
  }

  addReview(review: Review) : Observable<Review>{
    return this.http.post<Review>(URL,review);
  }

  getReviewsOfCourse(courseId: number): Observable<Review[]> {
    return this.http.get<Review[]>(URL+'?courseId='+ courseId);
  }
}
