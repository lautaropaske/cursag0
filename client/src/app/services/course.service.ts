import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Course} from "../models/Course";
import {ExtCourse} from "../models/ExtCourse";
import {LocalCourse} from "../models/LocalCourse";
import {Local} from "protractor/built/driverProviders";

const URL ="http://localhost:8080/course";

@Injectable()
export class CourseService {

  constructor(private http: HttpClient) { }

  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(URL);
  }

  delete(id: number): Observable<Object>{
    return this.http.delete(URL + '/'+id);
  }

  getCourse(id: number): Observable<Course> {
    return this.http.get<Course>(URL + '/'+id);
  }
  addExtCourse(course: ExtCourse) : Observable<ExtCourse>{
    return this.http.post<ExtCourse>(URL + '/external',course);
  }

  addLocalCourse(course: LocalCourse) : Observable<LocalCourse>{
    return this.http.post<LocalCourse>(URL + '/local',course);
  }

  getCoursesCreatedByUser(id: number): Observable<Course[]> {
    //TODO
    return this.http.get<Course[]>(URL + '/');
  }

}
