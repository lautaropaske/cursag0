import {Component} from '@angular/core';
import {User} from "../User";
import {Router} from "@angular/router";
import {CourseService} from "../course.service";
import {Course} from "./Course";

@Component({
  selector : 'course-form',
  templateUrl : './course-form.component.html'
})

export class CourseFormComponent{

  constructor(private router: Router, private courseService: CourseService) {}

  create_ext_course(name: string, description: string, price: number, rating: number, publisher: User, link: string, source: string) : void {
    this.courseService.addCourse(new Course(null,name, description, price, 0, publisher, link, source)).subscribe(
      user => {
        console.log("Course was successfully created");
        console.log(user);
        this.router.navigate(['profile']);
      },
      err => {
        console.log("error ocurred in post of signup");
      }
    );
  }
}
