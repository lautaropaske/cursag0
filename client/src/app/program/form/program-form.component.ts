import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ProgramService} from "../../services/program.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Program} from "../../models/Program";
import {User} from "../../models/User";

@Component({
  selector: 'program-form',
  templateUrl : './program-form.component.html'
})
export class ProgramFormComponent implements OnInit{

  programForm: FormGroup;
  program: Program;
  displayError : boolean;

  constructor(private router: Router, private programService: ProgramService,
              private fb : FormBuilder) {
  }

  ngOnInit(): void {
    this.displayError = false;
    this.createProgramForm();
    this.program = Program.createEmpty();
  }

  createProgramForm() {
    this.programForm = this.fb.group({
      name : ['', Validators.required],
      description : ['', Validators.required]
    });
  }

  publishProgram() : void {
    if(this.programForm.valid) {

      let id = localStorage.getItem("id");
      let publisher: User = new User(+id, null, null, null, null, false);
      this.program.publisher = publisher;

      console.log(this.program);
      this.programService.createProgram(this.program).subscribe(
        program => {
          console.log("Program was successfully created");
          console.log(program);
          this.router.navigate(['program_update', program.id]);
        },
        err => {
          console.log("error ocurred when adding program");
          this.displayError = true;
        }
      );
    }
    else{
      this.displayError = true;
    }
  }


}
