import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../../services/course.service";
import {Course} from "../../models/Course";

@Component({
  selector: 'course-detail',
  template: `
    
    <div class="container mt-5">
      <h5 class="card-title">{{course.name}}</h5>
      <h6 class="card-subtitle text-muted">Rating: {{course.rating}}</h6>
      <p class="card-text">Description: {{course.description}}</p>
      <p class="card-text">Price: {{course.description}}</p>
      <p class="card-text">Publisher: {{course.publisher.surname}}, {{course.publisher.name}}</p>
    </div>
  `
})
export class CourseDetailComponent implements OnInit{

  course: Course;

  constructor(private courseService: CourseService, private route: ActivatedRoute) {}


  ngOnInit(): void {
    const id = +this.route.snapshot.params["id"];

    this.courseService.getCourse(id).subscribe(
      course => {
        console.log("ExtCourse was found successfully.");
        console.log(course);
        this.course = course;
      },
      err => {
        console.log("Unable to get courses from database.");
        this.course = null;
      }
    );

  }
}
