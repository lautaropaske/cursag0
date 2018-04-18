import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CourseService} from "../../services/course.service";
import {ExtCourse} from "../../models/ExtCourse";
import {User} from "../../models/User";

@Component({
  selector : 'course-form',
  templateUrl : './course-form.component.html'
})

export class CourseFormComponent{

  courseForm : FormGroup;
  extCourse : ExtCourse;
  displayError : boolean;

  constructor(private router: Router, private courseService: CourseService, private fb : FormBuilder) {
    this.create_form();
    this.extCourse = ExtCourse.create_empty();
    this.displayError = false;
  }

  create_form() {
    this.courseForm = this.fb.group({
      name : ['', Validators.required],
      about : ['', Validators.required],
      price : [0.00, Validators.min(0)],
      url : ['', Validators.required],
      source : ['', Validators.required]
    });
  }

  create_ext_course() : void {
    let id = localStorage.getItem("id");
    let publisher: User = new User(+id,null,null,null,null);

    this.courseService.addExtCourse(this.extCourse).subscribe(
      user => {
        console.log("ExtCourse was successfully created");
        console.log(user);
        this.router.navigate(['profile']);
      },
      err => {
        console.log("error ocurred in post of signup");
        this.displayError = true;
      }
    );
  }
}
