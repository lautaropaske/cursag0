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

    .progress {
      background-color: #aaa;
    }
    .progress {height: 30px;}
    .progress .sr-only { position: relative; }
    
    .small, small {
      font-size: 120%;
      font-weight: 500;
    }
  `]
})
export class CourseDetailComponent implements OnInit{

  course: Course;
  status: number;
  sessionId: number;
  isPublisher: boolean;
  isLocal: boolean;
  isEnrolled: boolean;
  loadedStatus: boolean;
  loadedCourse: boolean;


  constructor(private courseService: CourseService,private router: Router, private route: ActivatedRoute) {}


  ngOnInit(): void {
    const idOfCourse = +this.route.snapshot.params["id"];
    this.sessionId = +localStorage.getItem("id");

    this.courseService.enrollmentStatus(this.sessionId, idOfCourse).subscribe(
      status=> {
        if(status == -1){
          this.isEnrolled = false;
        }
        else if(status == -2){
          console.log('course has been completed');
          this.isEnrolled = true;
        }
        else{
          this.isEnrolled = true;
        }
        this.status = status;
        this.loadedStatus= true;

        console.log('status code: ' +status);
      },
      err => {
        console.log("error when verifing enrollment");
        this.course = null;
      }
    );

    this.course = this.courseService.getLoadedCourseById(idOfCourse);
    if(this.course == null){
      this.courseService.getCourse(idOfCourse).subscribe(
        course => {
          console.log("Course was found successfully.");
          console.log(course);
          this.courseService.addLoadedCourses([course]);
          this.course = course;
          this.isPublisher = course.publisher.id == this.sessionId;
          this.isLocal = course.link == null;
          this.loadedCourse = true;
        },
        err => {
          console.log("Unable to get courses from database.");
          this.course = null;
        }
      );
    }
    this.isPublisher = this.course.publisher.id == this.sessionId;
    this.isLocal = this.course.link == null;
    this.loadedCourse = true;


  }

  deleteCourse(): void{
    this.courseService.delete(this.course.id).subscribe(
      data => {
        console.log("Delete probably went well");
        this.courseService.removeLoadedCourse(this.course.id);
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

  doCourse(): void{
    this.router.navigate(['/local', this.course.id, this.status]);
  }

  enroll(): void {
    this.courseService.enrollUser(this.sessionId, this.course.id).subscribe(
      result => {
        if(result){
          this.isEnrolled = true;
          this.status = 0;
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
