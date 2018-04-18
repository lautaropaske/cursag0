import {CourseService} from "../../services/course.service";
import {ActivatedRoute} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {Course} from "../../models/Course";


@Component({
  selector: 'local',
  templateUrl: './local-course.component.html'
})
export class LocalCourseComponent implements OnInit{

  course: Course;

  constructor(private courseService: CourseService, private route: ActivatedRoute) {}


  ngOnInit(): void {
    const id = +this.route.snapshot.params["id"];

    this.courseService.getCourse(id).subscribe(
      course => {
        console.log("Course was found successfully.");
        console.log(course);
        this.course = course;

        // course.units.forEach(unit => {
        //     console.log("https://www.youtube.com/embed/"+ this.getId(unit.videoLink))
        //   }
        // )
      },
      err => {
        console.log("Unable to get courses from database.");
        this.course = null;
      }
    );
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
