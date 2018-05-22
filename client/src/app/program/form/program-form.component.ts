import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {ProgramService} from "../../services/program.service";

@Component({
  selector: 'program-form',
  template: `
    <admin-navbar></admin-navbar>
    <div class="container">
      <p>TODO hacer el form</p>
    </div>
  `
})
export class ProgramFormComponent implements OnInit{

  ngOnInit(): void {

  }

  constructor(private programService: ProgramService) {
  }


}
