import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {Course} from "../models/Course";
import {CourseService} from "../services/course.service";


@Component({
  selector: 'home',
  templateUrl : './home.component.html'
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
