import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Course} from "../models/Course";
import {CourseService} from "../services/course.service";

@Component({
  selector: 'profile',
  templateUrl : './dashboard.component.html'
})
export class DashboardComponent implements OnInit{


  id: number;
  name = localStorage.getItem("name");
  surname = localStorage.getItem("surname");
  createdCourses: Course[] = [];
  enrolledCourses: Course[] = [];
  loadedCreated: boolean;
  loadedEnrolled: boolean;


  constructor(private router: Router,private courseService: CourseService) {}

  ngOnInit(): void {
    this.id = +localStorage.getItem("id");

    this.courseService.loadedCourses = [];
    this.courseService.getCoursesPublishedByUser(this.id).subscribe(
      courses => {
        console.log("Published courses found successfully.");
        console.log(courses);
        this.courseService.addLoadedCourses(courses);
        this.createdCourses = courses;
        this.loadedCreated = true;
      },
      err => {
        console.log("Error when getting published courses.");
        this.createdCourses = null;
      }
    );

    this.courseService.getCoursesEnrolledByUser(this.id).subscribe(
      courses => {
        console.log("Enrolled courses found successfully.");
        console.log(courses);
        this.courseService.addLoadedCourses(courses);
        this.enrolledCourses = courses;
        this.loadedEnrolled = true;
      },
      err => {
        console.log("Error when getting enrolled courses.");
        this.createdCourses = null;
      }
    );
  }

  createCourse(): void{
    this.router.navigate(['create_course']);

  }

}
