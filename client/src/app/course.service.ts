import {Injectable} from "@angular/core";
import {User} from "./User";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Course} from "./Course";

const URL ="http://localhost:8080/course";



@Injectable()
export class CourseService {

  constructor(private http: HttpClient) { }

  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(URL);


   // return Observable.create(observer => {
      // setTimeout(() => {
      //   let courses = [{id:"0",name:"JUnit",description:"Unit testing in java",price:"0",rating:"4.5",publisher:""},
      //                 {id:"1",name:"Play Framework",description:"Java/scala MVC framework",price:"20",rating:"4.9",publisher:""},
      //                 {id:"2",name:"Angular 5",description:"frontend javascript framework",price:"15",rating:"3.9",publisher:""}
      //     ];
      //   observer.next(courses); // This method same as resolve() method from Angular 1
      //   observer.complete();//to show we are done with our processing
      //   // observer.error(new Error("error message"));
      // }, 500);


   // })

  }


  getCourseOfUser(id: number): Observable<Course[]> {
    return this.http.get<Course[]>(URL + '/id');

  }

}
