import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
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
  sessionId: number;
  currentRate: number;
  isPublisher: boolean;
  isLocal: boolean;
  isEnrolled: boolean;
  currentUnit: number;

  constructor(private courseService: CourseService,private router: Router, private route: ActivatedRoute) {}


  ngOnInit(): void {
    const idOfCourse = +this.route.snapshot.params["id"];
    this.sessionId = +localStorage.getItem("id");

    this.courseService.getCourse(idOfCourse).subscribe(
      course => {
        console.log("Course was found successfully.");
        console.log(course);
        this.course = course;
        this.currentRate = this.course.rating;
        this.isPublisher = course.publisher.id == this.sessionId;
        this.isLocal = course.link == null;
      },
      err => {
        console.log("Unable to get courses from database.");
        this.course = null;
      }
    );

    this.courseService.enrollmentStatus(this.sessionId, idOfCourse).subscribe(
      status=> {
        if(status == -1){
          this.isEnrolled = false;
        }
        else{
          this.isEnrolled = true;
          this.currentUnit = status;
        }

      },
      err => {
        console.log("error when verifing enrollment");
        this.course = null;
      }
    );



  }

  deleteCourse(): void{
    this.courseService.delete(this.course.id).subscribe(
      data => {
        console.log("Delete probably went well");
        this.router.navigate(['/dashboard']);
      },
      err => {
        console.log("Error when making delete.");
      }
    )
  }

  editCourse(): void{
    this.router.navigate(['/edit_course', this.course.id]);
  }

  enroll(): void {
    this.courseService.enrollUser(this.sessionId, this.course.id).subscribe(
      result => {
        if(result){
          this.isEnrolled = true;
        }
      }

    )
  }

  unenroll(): void{
    this.courseService.unenrollUser(this.sessionId, this.course.id).subscribe(
      result => {
        if(result){
          this.isEnrolled = false;
        }
      }

    )
  }
}
