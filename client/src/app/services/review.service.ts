import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Review} from "../models/Review";
import {ReviewResp} from "../models/ReviewResp";

const URL ="http://localhost:8080/review";

@Injectable()
export class ReviewService {

  constructor(private http: HttpClient) {
  }

  addReview(review: Review) : Observable<Review>{
    return this.http.post<Review>(URL,review);
  }

  updateReview(review: Review) : Observable<Review>{
    return this.http.put<Review>(URL,review);
  }

  getReviewsOfCourse(courseId: number): Observable<ReviewResp[]> {
    return this.http.get<ReviewResp[]>(URL+'?courseId='+ courseId);
  }
}
