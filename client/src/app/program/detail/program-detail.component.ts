import {Component, OnInit} from "@angular/core";
import {ProgramService} from "../../services/program.service";
import {Program} from "../../models/Program";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'program-detail',
  template: `
    <admin-navbar></admin-navbar>
    <div class="container">
      <h2>TODO detail del program.</h2>
      <h2>{{program.name}}</h2>
      <p>{{program.description}}</p>
    </div>
    
  `
})
export class ProgramDetailComponent implements OnInit{

  program: Program;

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
  }

  constructor(private programService: ProgramService, private route: ActivatedRoute) {
  }


}
