import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Course} from "../../models/Course";
import {Program} from "../../models/Program";
import {ProgramService} from "../../services/program.service";

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
        </div>
      </div>
      
    </div>
    
    <endbar></endbar>
    
  `
})
export class ProgramDetailComponent implements OnInit {

  program: Program;
  courses: Course[];
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

      },
      err => {
        console.log("error when fetching courses");
        console.log(err)
      }
    );

  }

  constructor(private programService: ProgramService, private route: ActivatedRoute) {
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
