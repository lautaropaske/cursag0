import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Course} from "../models/Course";
import {ExtCourse} from "../models/ExtCourse";
import {LocalCourse} from "../models/LocalCourse";
import {Local} from "protractor/built/driverProviders";
import {Unit} from "../models/Unit";

const URL ="http://localhost:8080/course";

@Injectable()
export class CourseService {

  constructor(private http: HttpClient) { }

  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(URL);
  }

  getCoursesPublishedByUser(id: number): Observable<Course[]> {
    return this.http.get<Course[]>(URL + '/publishedBy/' + id);
  }

  getCoursesEnrolledByUser(id: number): Observable<Course[]> {
    return this.http.get<Course[]>(URL + '/enrolledBy/' + id);
  }

  enrollmentStatus(userId: number, courseId: number): Observable<number>{
    return this.http.get<number>(URL + '/enrollmentStatus?userId=' +
      userId+ '&courseId=' + courseId);
  }

  enrollUser(userId: number, courseId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/enroll?userId=' +
      userId+ '&courseId=' + courseId);
  }

  unenrollUser(userId: number, courseId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/unenroll?userId=' +
      userId+ '&courseId=' + courseId);
  }

  makeProgress(userId: number, courseId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/makeProgress?userId=' +
      userId+ '&courseId=' + courseId);
  }

  goBack(userId: number, courseId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/goBack?userId=' +
      userId+ '&courseId=' + courseId);
  }

  finished(userId: number, courseId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/finished?userId=' +
      userId+ '&courseId=' + courseId);
  }

  delete(id: number): Observable<Object>{
    return this.http.delete(URL + '/'+id);
  }

  updateExtCourse(course: ExtCourse): Observable<ExtCourse>{
    return this.http.put<ExtCourse>(URL+'/external', course);
  }

  updateLocalCourse(course: LocalCourse): Observable<LocalCourse>{
    return this.http.put<LocalCourse>(URL+'/local', course);
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

}
