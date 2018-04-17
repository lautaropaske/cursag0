import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../../services/course.service";
import {Course} from "../../models/Course";

@Component({
  selector: 'course-detail',
  templateUrl: './course-detail.component.html',
  styles: [`
    .star {
      position: relative;  
      display: inline-block;
      font-size: 2rem;
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
  `]
})
export class CourseDetailComponent implements OnInit{

  course: Course;
  currentRate: number;

  constructor(private courseService: CourseService, private route: ActivatedRoute) {}


  ngOnInit(): void {
    const id = +this.route.snapshot.params["id"];

    this.courseService.getCourse(id).subscribe(
      course => {
        console.log("Course was found successfully.");
        console.log(course);
        this.course = course;
        this.currentRate = this.course.rating;
      },
      err => {
        console.log("Unable to get courses from database.");
        this.course = null;
      }
    );

  }
}
