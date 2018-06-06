import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {ProgramService} from "../../services/program.service";

@Component({
  selector: 'program-form',
  templateUrl : './program-form.component.html'
})
export class ProgramFormComponent implements OnInit{

  ngOnInit(): void {

  }

  constructor(private programService: ProgramService) {
  }


}
