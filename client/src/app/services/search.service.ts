import {HttpClient} from "@angular/common/http";
import {Course} from "../models/Course";
import { HttpParams } from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";

@Injectable()
export class SearchService{
  constructor(private http: HttpClient) { }

  performSearch(token: string) : Observable<Course[]>{
    return this.http.get<Course[]>("http://localhost:8080/search?token="+token);
  }
}
