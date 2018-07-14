import {CourseService} from "../../services/course.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";
import {Course} from "../../models/Course";
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {Unit} from "../../models/Unit";


@Component({
  selector: 'local',
  templateUrl: './local-course.component.html'
})
export class LocalCourseComponent implements OnInit{


  course: Course;
  unit: Unit;
  progress: number;
  isPublisher: boolean;


  constructor(private courseService: CourseService,
              private route: ActivatedRoute, private router: Router, private sanitizer: DomSanitizer) {}


  ngOnInit(): void {
    const courseId = +this.route.snapshot.params["courseId"];
    const userId = +localStorage.getItem("id");

    this.courseService.getCourse(courseId).subscribe(
      course => {
        this.courseService.addLoadedCourses([course]);

        console.log("Course was found successfully.");
        console.log(course);
        this.course = course;

        this.isPublisher = course.publisher.id == userId;
        if(!this.isPublisher) {
          this.courseService.enrollmentStatus(userId, courseId).subscribe(
            status => {
              if (status >= 0) {
                this.progress = status;

              }
              else if (status == -2) {
                //Has finished the course
                this.router.navigate(['/course', this.course.id]);
              }

              this.unit = this.course.units[this.progress];
            },
            err => {
              console.log("error when verifing enrollment");
            }
          );
        }
        else{
          this.progress = 0;
        }

        this.unit = this.course.units[this.progress];

      },
      err => {
        console.log("Unable to get courses from database.");
        this.course = null;
      }
    );



  }

  nextUnit(): void {
    if (!this.isPublisher) {
      this.courseService.makeProgress(+localStorage.getItem("id"), this.course.id)
        .subscribe();
    }
    this.progress = this.progress + 1;
    this.unit = this.course.units[this.progress];
  }

  previousUnit(): void {
    if (!this.isPublisher) {
      this.courseService.goBack(+localStorage.getItem("id"), this.course.id)
        .subscribe();
    }
    this.progress = this.progress - 1;
    this.unit = this.course.units[this.progress];
  }

  finished(): void {
    if (!this.isPublisher) {
      this.courseService.finished(+localStorage.getItem("id"), this.course.id)
        .subscribe(
          resp => {
            this.courseService.removeLoadedCourse(this.course.id);
            this.router.navigate(['/course', this.course.id]);
          }
        );
    }
    else{
      this.router.navigate(['/course', this.course.id]);
    }
  }

  getUrl(): SafeResourceUrl{
    return this.sanitizer.bypassSecurityTrustResourceUrl("https://www.youtube.com/embed/"
                                                    + this.getId(this.unit.videoLink));
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
