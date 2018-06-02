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

  loadedCourses: Course[] = [];

  addLoadedCourses(courses: Course[]){
    courses.forEach(course => this.loadedCourses.push(course));
  }

  getLoadedCourseById(id: number): Course{
    const courses = this.loadedCourses.filter(course => course.id == id);
    if(courses.length > 0){
      return courses[0];
    }
    else{
      return null;
    }
  }

  removeLoadedCourse(id: number) {
    this.loadedCourses = this.loadedCourses.filter(course => course.id != id)
  }

  constructor(private http: HttpClient) { }

  getSomeCourses() : Observable<Course[]> {
    return this.http.get<Course[]>(URL + '/samplePopular');
  }

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
