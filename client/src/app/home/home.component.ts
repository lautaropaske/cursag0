import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {Course} from "../models/Course";
import {CourseService} from "../services/course.service";
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'home',
  templateUrl : './home.component.html',
  providers: [NgbCarouselConfig]
})
export class HomeComponent implements OnInit{

  carouselCourses: Course[] = [];
  courses: Course[] = [];

  constructor(config: NgbCarouselConfig, private courseService: CourseService) {
    config.interval = 5000;
    config.wrap = true;
  }

  ngOnInit(): void {
    this.courseService.getSomeCourses().subscribe(
      courses => {
        console.log("Popular courses where found successfully.");
        console.log(courses);
        this.courses = courses;
      },
      err => {
        console.log("Unable to get popular courses from database.");
        this.courses = null;
      }
    );

    this.courseService.getCarouselCourses().subscribe(
      courses => {
        console.log("Carousel courses where found successfully.");
        console.log(courses);
        this.carouselCourses = courses;
      },
      err => {
        console.log("Unable to get carousel courses from database.");
        this.courses = null;
      }
    );
  }
}
