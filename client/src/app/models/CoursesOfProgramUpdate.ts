import {Course} from "./Course";

export class CoursesOfProgramUpdate {
  id: number;
  courses: Course[];


  constructor(id: number, courses: Course[]) {
    this.id = id;
    this.courses = courses;
  }
}
