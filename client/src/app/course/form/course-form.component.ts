import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CourseService} from "../../services/course.service";
import {ExtCourse} from "../../models/ExtCourse";
import {User} from "../../models/User";
import {LocalCourse} from "../../models/LocalCourse";

@Component({
  selector : 'course-form',
  templateUrl : './course-form.component.html'
})

export class CourseFormComponent{

  courseForm_ext : FormGroup;
  courseForm_loc : FormGroup;
  extCourse : ExtCourse;
  locCourse : LocalCourse;
  displayError : boolean;
  postLocal : boolean;

  constructor(private router: Router, private courseService: CourseService, private fb : FormBuilder) {
    this.create_form_ext();
    this.create_form_loc();
    this.extCourse = ExtCourse.create_empty();
    this.locCourse = LocalCourse.create_empty();
    this.displayError = false;
    this.postLocal = true;
  }

  create_form_ext() {
    this.courseForm_ext = this.fb.group({
      name : ['', Validators.required],
      about : ['', Validators.required],
      price : [0.00, Validators.min(0)],
      url : ['', Validators.required, Validators.pattern('[/:.a-zA-Z]*[a-zA-Z]+\\.[a-zA-Z/?&#$!]+')], //TODO check REGEX: it could miss URL's in the future
      source : ['', Validators.required]
    });
  }

  create_form_loc() {
    this.courseForm_loc = this.fb.group({
      name : ['', Validators.required],
      about : ['', Validators.required],
      price : [0.00, Validators.min(0)],
    });
  }

  create_loc_course() : void {
    let id = localStorage.getItem("id");
    let publisher: User = new User(+id,null,null,null,null);

    this.locCourse.publisher = publisher;

    this.courseService.addLocalCourse(this.locCourse).subscribe(
      course => {
        console.log("LocalCourse was successfully created");
        console.log(course);
        this.router.navigate(['/details',course.id]);
      },
      err => {
        console.log("error ocurred in post of signup");
        this.displayError = true;
      }
    );
  }

  create_ext_course() : void {
    let id = localStorage.getItem("id");
    let publisher: User = new User(+id,null,null,null,null);

    this.extCourse.publisher = publisher;

    this.courseService.addExtCourse(this.extCourse).subscribe(
      course => {
        console.log("ExtCourse was successfully created");
        console.log(course);
        this.router.navigate(['/details',course.id]); // cambiar por una redirección al url del curso
      },
      err => {
        console.log("error ocurred in post of signup");
        this.displayError = true;
      }
    );
  }

  changePost() {
    this.postLocal = !this.postLocal;
    console.log(this.postLocal);
  }

}
