import {Component, OnInit} from "@angular/core";
import {ProgramService} from "../../services/program.service";
import {Program} from "../../models/Program";
import {ActivatedRoute} from "@angular/router";
import {Course} from "../../models/Course";

@Component({
  selector: 'program-detail',
  template: `
    <admin-navbar></admin-navbar>
    <div class="container">
      <h2>{{program.name}}</h2>
      <p>{{program.description}}</p>
      
      <h5>Lista de los cursos del programa: </h5>
      <div *ngFor="let course of courses" class="card w-75  mt-2" style="width: 18rem;">
        <div class="card-body">
          <a [routerLink]="['/details', course.id]">
            <h5 class="card-title">{{course.name}}</h5>
          </a>
          <p class="card-text">{{course.description}}</p>
          <p class="card-text">Rating: {{course.rating}}</p>
          <p class="card-text">Publisher: {{course.publisher.surname}}</p>
        </div>
      </div>
    </div>
    
  `
})
export class ProgramDetailComponent implements OnInit{

  program: Program;
  courses: Course[];

  ngOnInit(): void {
    const idOfProgram = +this.route.snapshot.params["id"];
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


}
