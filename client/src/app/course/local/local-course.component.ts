import {CourseService} from "../../services/course.service";
import {ActivatedRoute} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {Course} from "../../models/Course";
import { DomSanitizer } from '@angular/platform-browser';
import {Unit} from "../../models/Unit";
import {UnitService} from "../../services/unit.service";


@Component({
  selector: 'local',
  templateUrl: './local-course.component.html'
})
export class LocalCourseComponent implements OnInit{


  unit: Unit;
  courseId: number;
  userId: number;
  finished: boolean;


  constructor(private unitService: UnitService,private courseService: CourseService,
              private route: ActivatedRoute, private sanitizer: DomSanitizer) {}


  ngOnInit(): void {
    this.finished = false;
    this.courseId = +this.route.snapshot.params["id"];
    this.userId = +localStorage.getItem("id");

    let index = 0;
    this.courseService.enrollmentStatus(this.userId, this.courseId).subscribe(
      status=> {
        console.log("status number obtained: " + status);
        if(status == -2){
          this.finished = true;
          console.log('termino el curso');
        }
        else{

          this.unitService.getUnit(this.courseId, status).subscribe(
            unit => {
              console.log("Unit was found successfully.");
              console.log(unit);
              this.unit = unit;
            },
            err => {
              console.log("Unable to get unit from database.");
              this.unit = null;
            }
          );
        }

      },
      err => {
        console.log("error when getting enrollment status");
      }
    );
  }

  nextUnit(): void {
    // this.courseService.makeProgress(this.sessionId, this.course.id).subscribe(
    //   result => {
    //     if(result){
    //       this.isEnrolled = true;
    //     }
    //   }
    //
    // )
  }

  getId(url: string): string {
    const regExp: RegExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    const match = url.match(regExp);

    if (match && match[2].length == 11) {
      return match[2];
    } else {
      return 'error';
    }
  }
}
