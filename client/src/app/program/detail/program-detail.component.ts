import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Course} from "../../models/Course";
import {Program} from "../../models/Program";
import {ProgramService} from "../../services/program.service";

@Component({
  selector: 'program-detail',
  template: `
    <navbar></navbar>
    <div class="container mt-5">
      <h2>{{program.name}}</h2>
      <p>{{program.description}}</p>

      <div *ngIf="isEnrolled">
        <button type="button" (click)="unenroll();" class="btn mt-2 btn-danger">Unfavorite</button>
      </div>

      <div *ngIf="!isEnrolled">
        <button type="button" (click)="enroll();" class="btn mt-2 btn-success">Tag Program</button>
      </div>
      
      <h5>Courses of program:</h5>

      <p>listo a los cursos</p>
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

    //Get status of enrollment of program

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
