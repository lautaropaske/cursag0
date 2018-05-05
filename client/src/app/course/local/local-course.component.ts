import {CourseService} from "../../services/course.service";
import {ActivatedRoute} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {Course} from "../../models/Course";
import { DomSanitizer } from '@angular/platform-browser';
import {Unit} from "../../models/Unit";


@Component({
  selector: 'local',
  templateUrl: './local-course.component.html'
})
export class LocalCourseComponent implements OnInit{


  course: Course;
  unit: Unit;
  progress: number;


  constructor(private courseService: CourseService,
              private route: ActivatedRoute, private sanitizer: DomSanitizer) {}


  ngOnInit(): void {
    const courseId = +this.route.snapshot.params["courseId"];
    this.progress = +this.route.snapshot.params["progress"];

    this.courseService.getCourse(courseId).subscribe(
      course => {
        console.log("Course was found successfully.");
        console.log(course);
        this.course = course;
        this.unit = course.units[this.progress];
      },
      err => {
        console.log("Unable to get courses from database.");
        this.course = null;
      }
    );
  }

  nextUnit(): void {
    this.progress = this.progress + 1;
    this.unit = this.course.units[this.progress];
    this.courseService.makeProgress(+localStorage.getItem("id"),this.course.id)
                                    .subscribe()

  }

  previousUnit(): void {
    this.progress = this.progress - 1;
    this.unit = this.course.units[this.progress];
    this.courseService.goBack(+localStorage.getItem("id"),this.course.id)
      .subscribe()
  }

  finished(): void {
    this.courseService.finished(+localStorage.getItem("id"),this.course.id)
      .subscribe()
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
