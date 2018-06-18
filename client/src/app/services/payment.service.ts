import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {InitiateResp} from "../models/todoPago/InitiateResp";
import {VerifyResp} from "../models/todoPago/VerifyResp";

const URL ="http://localhost:8080/payment";

@Injectable()
export class PaymentService {

  constructor(private http: HttpClient) {}

  initiatePayment(userId: number, courseId: number): Observable<InitiateResp>{
    return this.http.get<InitiateResp>(URL + '/initiate?userId=' +
      userId+ '&courseId=' + courseId);
  }

  verifyPayment(userId: number,courseId: number, answerKey: string): Observable<VerifyResp>{
    return this.http.get<VerifyResp>(URL + '/verify?userId=' +
      userId +'&courseId='+courseId+'&answerKey=' + answerKey);
  }
}
