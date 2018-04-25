import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {CourseService} from "./services/course.service";
import {Course} from "./models/Course";

@Component({
  selector: 'home',
  template: `    
    <navbar></navbar>
    <h1>This is Cursago: Take a look at all of our courses</h1>


    <div *ngFor="let course of courses" class="card" style="width: 18rem;">
      <div class="card-body">
        <a [routerLink]="['/details', course.id]">
          <h5 class="card-title">{{course.name}}</h5>
        </a>
        <h6 class="card-subtitle mb-2 text-muted">{{course.rating}}</h6>
        <p class="card-text">{{course.description}}</p>
        <p class="card-text">Publisher: {{course.publisher.surname}}</p>
      </div>
    </div>
  `
})
export class HomeComponent implements OnInit{

  courses: Course[] = [];


  constructor(private courseService: CourseService) {}


  ngOnInit(): void {
    this.courseService.getAllCourses().subscribe(
      courses => {
        console.log("Courses where found successfully.");
        console.log(courses);
        this.courses = courses;
      },
      err => {
        console.log("Unable to get courses from database.");
        this.courses = null;
      }
    );

  }
}
