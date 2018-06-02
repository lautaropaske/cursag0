import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {Program} from "../models/Program";
import {Course} from "../models/Course";
import {CoursesOfProgramUpdate} from "../models/CoursesOfProgramUpdate";

const URL ="http://localhost:8080/program";

@Injectable()
export class ProgramService {

  constructor(private http: HttpClient) { }

  getAllPrograms(): Observable<Program[]> {
    return this.http.get<Program[]>(URL);
  }

  getProgramsWhereCourseIsNotPresent(courseId: number): Observable<Program[]> {
  return this.http.get<Program[]>(URL+ '/courseNotPresent/' + courseId);
}

  getProgramsPublishedByUser(id: number): Observable<Program[]> {
    return this.http.get<Program[]>(URL + '/publishedBy/' + id);
  }

  getProgramsEnrolledByUser(id: number): Observable<Program[]> {
    return this.http.get<Program[]>(URL + '/enrolledBy/' + id);
  }

  enrollUser(userId: number, programId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/enroll?userId=' +
      userId+ '&programId=' + programId);
  }

  unenrollUser(userId: number, programId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/unenroll?userId=' +
      userId+ '&programId=' + programId);
  }

  addCourseToProgram(programId: number, courseId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/addcourse?programId=' +
      programId+ '&courseId=' + courseId);
  }

  removeCourseFromProgram(programId: number, courseId: number): Observable<boolean>{
    return this.http.get<boolean>(URL + '/removecourse?programId=' +
      programId+ '&courseId=' + courseId);
  }

  getProgramById(id: number): Observable<Program> {
    return this.http.get<Program>(URL + '/'+id);
  }

  getCoursesOfProgram(id: number): Observable<Course[]>{
    return this.http.get<Course[]>(URL + '/courses/'+id);
  }

  createProgram(program: Program) : Observable<Program>{
    return this.http.post<Program>(URL ,program);
  }

  updateCoursesOfProgram(courses: CoursesOfProgramUpdate) : Observable<CoursesOfProgramUpdate>{
    return this.http.put<CoursesOfProgramUpdate>(URL+'/courses',courses);
  }

  editProgram(program: Program) : Observable<Program>{
    return this.http.put<Program>(URL ,program);
  }

  deleteProgram(id: number) : Observable<Object>{
    return this.http.delete(URL + '/'+id);
  }

  getCarouselPrograms() : Observable<Program[]> {
    return this.http.get<Program[]>(URL + '/carousel');
  }

}
