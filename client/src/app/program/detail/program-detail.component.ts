import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Course} from "../../models/Course";
import {Program} from "../../models/Program";
import {ProgramService} from "../../services/program.service";
import {CourseService} from "../../services/course.service";

@Component({
  selector: 'program-detail',
  template: `
    <navbar></navbar>
    <div class="jumbotron jumbotron-fluid">
      <div class="container">
        <h1 class="display-3">{{program.name}}</h1>
        <p class="lead">{{program.description}}</p>

        <div *ngIf="isEnrolled">
          <div style="font-size:3em; color:gold">
            <i style="cursor:pointer;" title="Unfavorite Program" (click)="unenroll()" class="fas fa-star"></i>
          </div>
        </div>

        <div *ngIf="!isEnrolled">
          <div style="font-size:3em; color:gold">
            <i style="cursor:pointer;" title="Mark as favorite" (click)="enroll()" class="far fa-star"></i>
          </div>
        </div>
        
      </div>
      
     
    </div>
    
    <div class="container mt-5">
      <h3>Courses of program:</h3>

      <div *ngFor="let course of courses" class="card w-75  mt-2" style="width: 18rem;">
        <div class="card-body">
          <a [routerLink]="['/details', course.id]">
            <h5 class="card-title">{{course.name}}</h5>
          </a>
          <p class="card-text">{{course.description}}</p>
          <!--LOCAL-->
          <div *ngIf="course.link == null">
            <div *ngIf="statusOfCourses[course.id] == -1">
              <p class="lead">You are not enrolled in this course</p>
            </div>
            <div *ngIf="statusOfCourses[course.id] == -2">
              <p class="lead">You have completed this course</p>
            </div>
            <div *ngIf="statusOfCourses[course.id] >= 0">
              <div class="progress">
                <div class="progress-bar progress-bar-striped" [style.width]="statusOfCourses[course.id]/course.units.length*100 + '%'">
                  <small class="justify-content-center d-flex position-absolute w-100">
                    completed {{statusOfCourses[course.id]}} out of {{course.units.length}} units
                  </small>
                </div>
              </div>
            </div>
          </div>
          <!--EXTERNO-->
          <div *ngIf="course.link != null">
            <div *ngIf="statusOfCourses[course.id] == -1">
              <p class="lead">You have not favorited this external course</p>
            </div>
            <div *ngIf="statusOfCourses[course.id] == 0">
              <p class="lead">Your have favorited this external course</p>
            </div>
          </div>
          
        </div>
      </div>
      
    </div>
    
    <endbar></endbar>
    
  `,
  styles: [`    
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
export class ProgramDetailComponent implements OnInit {

  program: Program;
  courses: Course[];
  statusOfCourses: { [key: number]:number; } = {};
  isEnrolled: boolean;
  sessionId: number;

  ngOnInit(): void {
    const idOfProgram = +this.route.snapshot.params["id"];
    this.sessionId = +localStorage.getItem("id");

    this.programService.isFavorite(this.sessionId, idOfProgram).subscribe(
      isEnrolled => {
        console.log("Status obtained successfully");
        this.isEnrolled = isEnrolled;

      },
      err => {
        console.log("error when getting status of enrollment");
        console.log(err)
      }
    );

    this.programService.getProgramById(idOfProgram).subscribe(
      program => {
        console.log("Obtained program successfully");
        this.program = program;

      },
      err => {
        console.log("error when fetching program");
        console.log(err)
      }
    );

    this.programService.getCoursesOfProgram(idOfProgram).subscribe(
      courses => {
        console.log("Obtained courses of program successfully");
        this.courses = courses;

        this.courses.forEach(course => {
          this.courseService.enrollmentStatus(this.sessionId, course.id).subscribe(
            status=> {
              this.statusOfCourses[course.id] = status;
            },
            err => {
              console.log("error when getting enrollment status");
            }
          );
        })

      },
      err => {
        console.log("error when fetching courses");
        console.log(err)
      }
    );

  }

  constructor(private programService: ProgramService,private courseService: CourseService, private route: ActivatedRoute) {
  }

  enroll(): void {
    this.programService.enrollUser(this.sessionId, this.program.id).subscribe(
      result => {
        if(result){
          this.isEnrolled = true;
        }
      }

    )
  }

  unenroll(): void{
    this.programService.unenrollUser(this.sessionId, this.program.id).subscribe(
      result => {
        if(result){
          this.isEnrolled = false;
        }
      }

    )
  }

}
