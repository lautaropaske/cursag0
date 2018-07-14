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

  updateOrderOfUnits(units: Unit[]): Observable<Unit[]>{
    return this.http.put<Unit[]>(URL + '/updateorder',units);
  }

  deleteUnit(id: number) : Observable<Object>{
    return this.http.delete(URL + '/'+id);
  }

}
