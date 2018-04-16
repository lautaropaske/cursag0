import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CourseService} from "./services/course.service";
import {Course} from "./models/Course";

@Component({
  selector: 'profile',
  template: `
    <nav class="navbar navbar-light bg-light">
      <a class="navbar-brand">Cursago</a>
      <form class="form-inline">
        <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
      </form>
      <a class="nav-item nav-link" [routerLink]="" (click)="logout();">Logout</a>
    </nav>
    <div class="container mt-5">
        <h4>Currently logged in to {{name}} {{surname}} (id: {{id}})</h4>
        <p>List of your created courses (Por ahora listeo todos):</p>
      
        <div *ngFor="let course of createdCourses" class="card" style="width: 18rem;">
          <div class="card-body">
            <a [routerLink]="['/details', course.id]">
              <h5 class="card-title">{{course.name}}</h5>
            </a>
            <h6 class="card-subtitle mb-2 text-muted">{{course.rating}}</h6>
            <p class="card-text">{{course.description}}</p>
            <p class="card-text">Publisher: {{course.publisher.surname}}</p>
          </div>
        </div>
      

        <button type="button" (click)="createCourse();" class="btn mt-5 btn-primary">Add new course</button>
    </div> 
  `
})
export class ProfileComponent implements OnInit{


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
