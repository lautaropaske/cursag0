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

  extCourseForm : FormGroup;
  locCourseForm : FormGroup;
  extCourse : ExtCourse;
  locCourse : LocalCourse;
  displayError : boolean;
  postLocal : boolean;

  constructor(private router: Router, private courseService: CourseService, private fb : FormBuilder) {
    this.createExtForm();
    this.createLocalForm();
    this.extCourse = ExtCourse.create_empty();
    this.locCourse = LocalCourse.create_empty();
    this.displayError = false;
    this.postLocal = true;
  }

  createExtForm() {
    this.extCourseForm = this.fb.group({
      name : ['', Validators.required],
      about : ['', Validators.required],
      price : [0.00, Validators.min(0)],
      url : ['', Validators.required, Validators.pattern('[/:.a-zA-Z]*[a-zA-Z]+\\.[a-zA-Z/?&#$!]+')], //TODO check REGEX: it could miss URL's in the future
      source : ['', Validators.required]
    });
  }

  createLocalForm() {
    this.locCourseForm = this.fb.group({
      name : ['', Validators.required],
      about : ['', Validators.required],
      price : [0.00, Validators.min(0)],
    });
  }

  publishLocalCourse() : void {
    if(this.locCourseForm.valid) {
      let id = localStorage.getItem("id");
      let publisher: User = new User(+id, null, null, null, null, false);

      this.locCourse.publisher = publisher;

      this.courseService.addLocalCourse(this.locCourse).subscribe(
        course => {
          console.log("LocalCourse was successfully created");
          console.log(course);
          this.router.navigate(['/details', course.id]);
        },
        err => {
          console.log("LocalCourse could not be created");
          this.displayError = true;
        }
      );
    }
    else {
      this.displayError = true;
    }
  }

  publishExternalCourse() : void {
    if (this.extCourseForm.valid) {
      let id = localStorage.getItem("id");
      let publisher: User = new User(+id, null, null, null, null, false);

      this.extCourse.publisher = publisher;

      this.courseService.addExtCourse(this.extCourse).subscribe(
        course => {
          console.log("ExtCourse was successfully created");
          console.log(course);
          this.router.navigate(['/details', course.id]); // cambiar por una redirección al url del curso
        },
        err => {
          console.log("ExtCourse could not be created");
          this.displayError = true;
        }
      );
    }
    else {
      this.displayError = true;
    }


  }

  changePost() {
    this.postLocal = !this.postLocal;
    console.log(this.postLocal);
  }

}
