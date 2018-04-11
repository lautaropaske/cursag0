import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {UserService} from "./user.service";
import {CourseService} from "./course.service";
import {Course} from "./Course";

@Component({
  selector: 'courses',
  template: `
    <h1>This is Cursago: Take a look at all of our courses</h1>
    
  `
})
export class HomeComponent implements OnInit{

  courses: Course[] = [];

  constructor(private courseService: CourseService) {}


  ngOnInit(): void {
    //aca usaria course service
    this.courses = [
      {id:0,name:"JUnit",description:"Unit testing in java",price:0,rating:4.5,publisher:null},
      {id:1,name:"Play Framework",description:"Java/scala MVC framework",price:20,rating:4.9,publisher:null},
      {id:2,name:"Angular 5",description:"frontend javascript framework",price:15,rating:3.9,publisher:null}
      ];
  }
}
