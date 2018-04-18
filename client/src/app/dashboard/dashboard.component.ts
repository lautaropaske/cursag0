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


  constructor(private router: Router,private courseService: CourseService) {}

  ngOnInit(): void {
    this.id = +localStorage.getItem("id");
    //TODO esto va pedir los cursos creados por el usuario
    this.courseService.getAllCourses().subscribe(
      courses => {
        console.log("Courses where found successfully.");
        console.log(courses);
        this.createdCourses = courses;
      },
      err => {
        console.log("Unable to get courses from database.");
        this.createdCourses = null;
      }
    );
  }

  createCourse(): void{
    this.router.navigate(['create_course']);

  }

  logout():void{
    localStorage.clear();
    this.router.navigate(['access']);
  }



}
