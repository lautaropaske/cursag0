import {Course} from "../models/Course";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Unit} from "../models/Unit";

const URL ="http://localhost:8080/unit";

@Injectable()
export class UnitService {

  constructor(private http: HttpClient) {
  }

  addUnit(unit: Unit) : Observable<Unit>{
    return this.http.post<Unit>(URL,unit);
  }


  //
  // getUnit(courseId: number, index: number): Observable<Unit>{
  //   return this.http.get<Unit>(URL + '?courseId=' +
  //     courseId+ '&index=' + index);
  // }
}
