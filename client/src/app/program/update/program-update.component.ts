import {Component, OnInit} from "@angular/core";
import {ProgramService} from "../../services/program.service";
import {Program} from "../../models/Program";
import {ActivatedRoute, Router} from "@angular/router";
import {Course} from "../../models/Course";
import {CoursesOfProgramUpdate} from "../../models/CoursesOfProgramUpdate";

@Component({
  selector: 'program-update',
  template: `
    <admin-navbar></admin-navbar>
    <div class="container mt-5">
      <h2>{{program.name}}</h2>
      <p>{{program.description}}</p>
      
      <h5>Courses of program </h5>

      <div class="card" style="width: 45rem;">
        <ul [dndList]
            [dndModel]="courses" class="list-group list-group-flush">
          <li *ngFor="let course of courses;let i = index"
              [dndDraggable]="{draggable:true, effectAllowed:'move'}"
              [dndObject]="course"
              (dndMoved)="removeItem(course, courses)"
              class="list-group-item d-flex justify-content-between" style="cursor: pointer">
            <div [routerLink]="['/details', course.id]">
              {{course.name}}
            </div>
            <i (click)="removeCourse(course)" class="far fa-trash-alt"></i>
          </li>
        </ul>
      </div>
      
      <div *ngIf="alteredOrder">
        <button type="button" (click)="persistOrder();" class="btn mt-3 btn-outline-warning">Save order</button>
      </div>
      <button type="button" (click)="deleteProgram();" class="btn mt-3 btn-outline-danger">Delete program</button>
    </div>
    <endbar></endbar>
    


  `
})
export class ProgramUpdateComponent implements OnInit{

  program: Program;
  courses: Course[];
  alteredOrder: boolean;

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

  constructor(private programService: ProgramService, private route: ActivatedRoute,private router: Router) {
  }

  public removeItem(item: any, list: any[]): void {
    list.splice(list.indexOf(item), 1);
    this.alteredOrder = true;
  }

  public persistOrder(): void {
    let courses = this.courses.map<Course>(course => this.getSendable(course));
    let updatedCourses = new CoursesOfProgramUpdate(this.program.id, courses);
    this.programService.updateCoursesOfProgram(updatedCourses).subscribe(
      resp => {
        console.log(this.courses);
        console.log("update of courses was successful");
        this.alteredOrder = false;
      },
      err => {
        console.log("error when updating courses");
        console.log(err)
      }
    );
  }

  private getSendable(course: Course): Course {
    return new Course(course.id);
  }

  public deleteProgram(): void {
    this.programService.deleteProgram(this.program.id).subscribe(
      data => {
        console.log("Delete probably went well");
        this.router.navigate(['adminpanel']);
      },
      err => {
        console.log("Error when making delete.");
      }
    )
  }

  public removeCourse(course: Course): void {
    this.programService.removeCourseFromProgram(this.program.id, course.id).subscribe(
      resp => {
        console.log("course has been removed from program");
        const index = this.courses.indexOf(course, 0);
        if (index > -1) {
          this.courses.splice(index, 1);
        }
      }

    )
  }


}
