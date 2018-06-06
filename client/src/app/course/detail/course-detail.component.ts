import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../../services/course.service";
import {Course} from "../../models/Course";
import {FormControl} from "@angular/forms";
import {ProgramService} from "../../services/program.service";
import {Program} from "../../models/Program";
import {Observable} from 'rxjs/Observable';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';
import {PaymentService} from "../../services/payment.service";

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

    .example-form {
      min-width: 150px;
      max-width: 500px;
      width: 100%;
    }

    .example-full-width {
      width: 100%;
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

  isAdmin: boolean;
  programs: Program[];

  myControl: FormControl = new FormControl();
  filteredOptions: Observable<string[]>;

  filter(val: string): string[] {
    return this.programs.filter(program =>
      program.name.toLowerCase().indexOf(val.toLowerCase()) === 0).map(program => program.name);
  }

  constructor(private courseService: CourseService, private programService: ProgramService,
              private paymentService: PaymentService, private router: Router, private route: ActivatedRoute) {}


  ngOnInit(): void {

    const idOfCourse = +this.route.snapshot.params["id"];
    this.sessionId = +localStorage.getItem("id");

    if(localStorage.getItem("type") == "admin"){
      this.isAdmin = true;
      this.programService.getProgramsWhereCourseIsNotPresent(idOfCourse).subscribe(
        programs=> {
          this.programs = programs;
          this.filteredOptions = this.myControl.valueChanges
            .pipe(
              startWith(''),
              map(val => this.filter(val))
            );

        },
        err => {
          console.log("error when obtaining programs");
          this.course = null;
        }
      );

    }
    else{
      this.isAdmin = false;
      this.programs = null;
    }
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

  buyCourse(): void {
    this.paymentService.initiatePayment(this.sessionId, this.course.id).subscribe(
      response => {
        if(response.StatusCode == "-1"){
          window.location.href = response.URL_Request;
        }
        else{
          console.log("Todo pago responded with an error.");
          console.log(response);
        }
      },
      err => {
        console.log("Error when initiating payment.");
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

  addToProgram(value: string) {
    let selectedProgram = this.programs.find(program => program.name === value);
    if (typeof selectedProgram !== "undefined") {
      this.programService.addCourseToProgram(selectedProgram.id, this.course.id).subscribe(
        result => {
          this.router.navigate(['program_update', selectedProgram.id]);
        }
      );
    }
  }
}
