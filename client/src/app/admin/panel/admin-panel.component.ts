import {Router} from "@angular/router";
import {Component, Input, OnInit} from "@angular/core";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Program} from "../../models/Program";
import {ProgramService} from "../../services/program.service";

@Component({
  selector: 'adminpanel',
  templateUrl : './admin-panel.component.html'
})
export class AdminPanelComponent implements OnInit{

  createdPrograms: Program[];


  ngOnInit(): void {
    let sessionId = +localStorage.getItem("id");
    this.programService.getProgramsPublishedByUser(sessionId).subscribe(
      programs => {
        console.log("Programs found successfully.");
        console.log(programs);
        this.createdPrograms = programs
      },
      err => {
        console.log("Error when getting programs.");
        console.log(err)
      }
    );
  }

  constructor(private router: Router, private programService: ProgramService) {
  }


}
