import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Course} from "../../models/Course";
import {Component, OnInit} from "@angular/core";
import {CourseService} from "../../services/course.service";
import {Unit} from "../../models/Unit";
import {ActivatedRoute, Router} from "@angular/router";
import {ExtCourse} from "../../models/ExtCourse";

@Component({
  selector: 'edit-course',
  templateUrl: './edit-course.component.html'
})
export class EditCourseComponent implements OnInit{

  course: Course;
  isLocal: boolean;
  displayError : boolean;
  extCourseForm : FormGroup;
  locCourseForm : FormGroup;

  constructor(private router: Router,private route: ActivatedRoute, private courseService: CourseService,private fb : FormBuilder) {
    this.displayError = false;
    this.createExtForm();
    this.createLocalForm();
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.params["id"];

    this.courseService.getCourse(id).subscribe(
      course => {
        this.course = course;
        console.log(course);
        this.isLocal = course.link == null;
      },
      err => {
        console.log("Unable to get course from database.");
        this.course = null;
      }
    );

  }

  createExtForm() {
    this.extCourseForm = this.fb.group({
      name : ['', Validators.required],
      about : ['', Validators.required],
      price : [0.00, Validators.min(0)],
      url : ['', Validators.required],
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

  publishEdit() : void {
    console.log(this.course)
    if(!this.isLocal) {
      this.courseService.updateExtCourse(this.course).subscribe(
        course => {
          console.log("Course has been updated");
          console.log(course);
          this.courseService.removeLoadedCourse(this.course.id);
          this.router.navigate(['/course', course.id]); // cambiar por una redirección al url del curso
        },
        err => {
          console.log("error ocurred when updating");
          this.displayError = true;
        }
      )
    }
    else{
      this.courseService.updateLocalCourse(this.course).subscribe(
        course => {
          console.log("Course has been updated");
          console.log(course);
          this.courseService.removeLoadedCourse(this.course.id);
          this.courseService.addLoadedCourses([this.course]);
          this.router.navigate(['/course', course.id]); // cambiar por una redirección al url del curso
        },
        err => {
          console.log("error ocurred when updating");
          this.displayError = true;
        }
      )
    }
  }





}
