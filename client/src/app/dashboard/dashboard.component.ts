import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Course} from "../models/Course";
import {CourseService} from "../services/course.service";
import {ProgramService} from "../services/program.service";
import {Program} from "../models/Program";

@Component({
  selector: 'profile',
  templateUrl : './dashboard.component.html',
  styles: [`
    .star {
      position: relative;  
      display: inline-block;
      font-size: 1rem;
      color: #d3d3d3;
    }
    .full {
      color: red;
    }
    .half {
      position: absolute;
      display: inline-block;
      overflow: hidden;
      color: red;
    }

    .progress {
      background-color: #aaa;
    }
    
    .banner {
      background: #466368;
      background: linear-gradient(to right, #e0e0e0, #f9f9f9);
      /*border-radius: 6px;*/
      height: 100px;
    }
    
    .card-columns {
        column-count: 3;
    }
  `]
})
export class DashboardComponent implements OnInit{


  id: number;
  name = localStorage.getItem("name");
  surname = localStorage.getItem("surname");
  createdCourses: Course[] = [];
  enrolledCourses: Course[] = [];
  enrolledPrograms: Program[] = [];



  constructor(private router: Router,private courseService: CourseService, private programService: ProgramService) {}

  ngOnInit(): void {
    this.id = +localStorage.getItem("id");

    this.courseService.loadedCourses = [];
    this.courseService.getCoursesPublishedByUser(this.id).subscribe(
      courses => {
        console.log("Published courses found successfully.");
        console.log(courses);
        this.courseService.addLoadedCourses(courses);
        this.createdCourses = courses;
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
      },
      err => {
        console.log("Error when getting enrolled courses.");
        this.createdCourses = null;
      }
    );

    this.programService.getProgramsEnrolledByUser(this.id).subscribe(
      programs => {
        console.log("Enrolled programs found successfully.");
        console.log(programs);
        this.enrolledPrograms = programs;
      },
      err => {
        console.log("Error when getting enrolled programs.");
        this.createdCourses = null;
      }
    );


  }

  createCourse(): void{
    this.router.navigate(['create_course']);
  }

}
