import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {Course} from "../models/Course";
import {CourseService} from "../services/course.service";
import {NgbCarouselConfig} from '@ng-bootstrap/ng-bootstrap';
import {ProgramService} from "../services/program.service";
import {Program} from "../models/Program";


@Component({
  selector: 'home',
  templateUrl : './home.component.html',
  providers: [NgbCarouselConfig],
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
      background-color: rgba(255, 255, 255, 0.98);
    }

    .bgImgCenter{
      background-image: url("assets/fondoEditado.jpg");
      background-size: 100% 92vh;
      width: 100%;
      height: 92vh;
      padding-top: 126px;
    }

    .verticalLine {
      border-left: 7px solid white;
      margin-left:30px;
    }

  `]
})
export class HomeComponent implements OnInit{

  normalPrograms: Program[] = [];
  courses: Course[] = [];
  limit: number = 150;

  constructor(config: NgbCarouselConfig, private programService: ProgramService, private courseService: CourseService) {
    config.interval = 5000;
    config.wrap = true;
  }

  ngOnInit(): void {
    this.courseService.loadedCourses = [];

    this.courseService.getSomeCourses().subscribe(
      courses => {
        console.log("Popular courses where found successfully.");
        console.log(courses);
        this.courseService.addLoadedCourses(courses);
        this.courses = courses;
      },
      err => {
        console.log("Unable to get popular courses from database.");
      }
    );

    this.programService.getAllPrograms().subscribe(
      programs => {
        console.log("Normal programs where found successfully.");
        console.log(programs);
        this.normalPrograms = programs;
      },
      err => {
        console.log("Unable to get normal programs from database.");
      }
    );
  }
}
