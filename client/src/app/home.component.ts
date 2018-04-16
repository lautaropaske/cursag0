import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {UserService} from "./user.service";
import {CourseService} from "./course.service";
import {Course} from "./Course";
import {User} from "./User";

@Component({
  selector: 'home',
  template: `    
    <h1>This is Cursago: Take a look at all of our courses</h1>
    
    
    <div *ngFor="let course of courses" class="card" style="width: 18rem;">
      <div class="card-body">
        <h5 class="card-title">{{course.name}}</h5>
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

    // this.courses = [
    //   {id:0,name:"JUnit",description:"Unit testing in java",price:0,rating:4.5,publisher:new User(null,'','','Agustin','Bettati')},
    //   {id:1,name:"Play Framework",description:"Java/scala MVC framework",price:20,rating:4.9,publisher:new User(null,'','','Agustin','Perez')},
    //   {id:2,name:"Angular 5",description:"frontend javascript framework",price:15,rating:3.9,publisher:new User(null,'','','Agustin','Carlos')}
    //   ];
  }
}
