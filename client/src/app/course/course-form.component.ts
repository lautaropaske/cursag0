import {Component} from '@angular/core';
import {User} from "../User";
import {Router} from "@angular/router";
import {CourseService} from "../course.service";
import {ExtCourse} from "./ExtCourse";

@Component({
  selector : 'course-form',
  templateUrl : './course-form.component.html'
})

export class CourseFormComponent{

  constructor(private router: Router, private courseService: CourseService) {}

  create_ext_course(name: string, description: string, price: number, rating: number, link: string, source: string) : void {
    let id = localStorage.getItem("id");
    let publisher: User = new User(+id,null,null,null,null);
    this.courseService.addExtCourse(new ExtCourse(null,name, description, price, 0, publisher, link, source)).subscribe(
      user => {
        console.log("ExtCourse was successfully created");
        console.log(user);
        this.router.navigate(['profile']);
      },
      err => {
        console.log("error ocurred in post of signup");
      }
    );
  }
}
