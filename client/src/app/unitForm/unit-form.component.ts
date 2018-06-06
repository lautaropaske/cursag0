import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CourseService} from "../services/course.service";
import {ExtCourse} from "../models/ExtCourse";
import {Unit} from "../models/Unit";
import {User} from "../models/User";
import {Course} from "../models/Course";
import {UnitService} from "../services/unit.service";

@Component({
  selector: 'unit-form',
  templateUrl: './unit-form.component.html'
})
export class UnitFormComponent implements OnInit{

  unitForm: FormGroup;
  unit: Unit;
  idOfParentCourse: number;
  displayError : boolean;

  constructor(private router: Router,private route: ActivatedRoute, private unitService: UnitService,
              private fb : FormBuilder, private courseService: CourseService) {
  }


  ngOnInit(): void {
    this.displayError = false;
    this.idOfParentCourse = +this.route.snapshot.params["id"];
    this.createUnitForm();
    this.unit = Unit.createEmpty();
  }

  createUnitForm() {
    this.unitForm = this.fb.group({
      name : ['', Validators.required],
      videoLink : ['', Validators.required],
      number : [0, Validators.min(0)],
      textContent : ['', Validators.required]
    });
  }

  publishUnit() : void {
    if(this.unitForm.valid) {
      let parentCourse: Course = new Course(+this.idOfParentCourse);
      this.unit.parent = parentCourse;
      console.log(this.unit);
      this.unitService.addUnit(this.unit).subscribe(
        unit => {
          console.log("Unit was successfully created");
          console.log(unit);
          this.courseService.removeLoadedCourse(this.idOfParentCourse);
          this.router.navigate(['/details', this.idOfParentCourse]);
        },
        err => {
          console.log("error ocurred when adding unit");
          this.displayError = true;
        }
      );
    }
    else{
      this.displayError = true;
    }
  }

}
