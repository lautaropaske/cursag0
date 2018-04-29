import {HttpClient} from "@angular/common/http";
import {Course} from "../models/Course";
import { HttpParams } from '@angular/common/http';
import {Observable} from "rxjs/Observable";


export class SearchService{
  constructor(private http: HttpClient) { }

  performSearch(token: string) : Observable<Course[]>{
    const params = new HttpParams().set('token', token);
    return this.http.get<Course[]>("localhost:8080/search",{params});
  }
}
