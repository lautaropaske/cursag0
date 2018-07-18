import {ChangeDetectorRef, Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../../services/course.service";
import {Course} from "../../models/Course";
import {FormControl} from "@angular/forms";
import {ProgramService} from "../../services/program.service";
import {Program} from "../../models/Program";
import {Observable} from 'rxjs/Observable';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';
import {PaymentService} from "../../services/payment.service";
import {PaymentOfCourse} from "../../models/PaymentOfCourse";
import {ViewChild} from '@angular/core';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {UnitService} from "../../services/unit.service";
import {Unit} from "../../models/Unit";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'course-detail',
  templateUrl: './course-detail.component.html',
  styles: [`
    .star {
      position: relative;  
      display: inline-block;
      font-size: 2rem;
      color: #d3d3d3;
    }
    .full {
      color: red;
    }
    .half {
      position: absolute;
      display: inline-block;
      overflow: hidden;
      color: red;
    }

    .progress {
      background-color: #aaa;
    }
    .progress {height: 30px;}
    .progress .sr-only { position: relative; }
    
    .small, small {
      font-size: 120%;
      font-weight: 500;
    }

    .example-form {
      min-width: 150px;
      max-width: 500px;
      width: 100%;
    }

    .example-full-width {
      width: 100%;
    }
  `]
})
export class CourseDetailComponent implements OnInit{

  course: Course;
  status: number;
  sessionId: number;
  isLocal: boolean;
  isEnrolled: boolean;
  loadedStatus: boolean;
  loadedCourse: boolean;
  startsPayment: boolean;

  isPublisher: boolean;
  payments: PaymentOfCourse[];

  isRegistered: boolean;

  showPayments: boolean;
  totalRevenue: number = 0;
  dataSource: MatTableDataSource<PaymentOfCourse>;
  displayedColumns = ['user', 'date', 'price'];
  @ViewChild('Paginator') paginator: MatPaginator;

  alteredOrder: boolean;

  image: string;
  selectedFile: File;


  isAdmin: boolean;
  programs: Program[];
  myControl: FormControl = new FormControl();
  filteredOptions: Observable<string[]>;

  filter(val: string): string[] {
    return this.programs.filter(program =>
      program.name.toLowerCase().indexOf(val.toLowerCase()) === 0).map(program => program.name);
  }

  constructor(private courseService: CourseService, private programService: ProgramService, private unitService: UnitService,
              private paymentService: PaymentService, private router: Router, private route: ActivatedRoute,
              private http: HttpClient, private cdRef: ChangeDetectorRef) {}


  ngOnInit(): void {

    const idOfCourse = +this.route.snapshot.params["id"];
    this.sessionId = +localStorage.getItem("id");

    this.image = 'http://localhost:8080/image/' + idOfCourse;

    if(this.sessionId != 0){
      this.isRegistered = true;
    }

    if(localStorage.getItem("type") == "admin"){
      this.isAdmin = true;
      this.programService.getProgramsWhereCourseIsNotPresent(idOfCourse).subscribe(
        programs=> {
          this.programs = programs;
          this.filteredOptions = this.myControl.valueChanges
            .pipe(
              startWith(''),
              map(val => this.filter(val))
            );

        },
        err => {
          console.log("error when obtaining programs");
          this.course = null;
        }
      );

    }
    else{
      this.isAdmin = false;
      this.programs = null;
    }
    if(this.isRegistered) {
      this.courseService.enrollmentStatus(this.sessionId, idOfCourse).subscribe(
        status => {
          if (status == -1) {
            this.isEnrolled = false;
          }
          else if (status == -2) {
            console.log('course has been completed');
            this.isEnrolled = true;
          }
          else {
            this.isEnrolled = true;
          }
          this.status = status;
          this.loadedStatus = true;

          console.log('status code: ' + status);
        },
        err => {
          console.log("error when verifing enrollment");
          this.course = null;
        }
      );
    }
    else{
      this.isPublisher = false;
      this.isEnrolled = false;
      this.isAdmin = false;
      this.loadedStatus = true;
    }

    this.courseService.getCourse(idOfCourse).subscribe(
      course => {
        console.log("Course was found successfully.");
        console.log(course);
        this.courseService.addLoadedCourses([course]);
        this.course = course;
        this.isPublisher = course.publisher.id == this.sessionId;
        this.isLocal = course.link == null;
        this.loadedCourse = true;

        // this.image  = 'http://localhost:8080/image/' + course.id;

        if(this.isPublisher && this.isLocal && this.course.price > 0){
          this.paymentService.getPaymentsOfCourse(this.course.id).subscribe(
            payments => {
              this.payments = payments;
              this.payments.forEach(payment => this.totalRevenue += payment.amount);
              console.log("Found payments");
              console.log(this.payments);
              this.dataSource = new MatTableDataSource<PaymentOfCourse>(payments);
              this.dataSource.paginator = this.paginator;
            },
            err => {
              console.log("Unable to get payments.");
              this.course = null;
            }
          )
        }
      },
      err => {
        console.log("Unable to get courses from database.");
        this.course = null;
      }
    );
  }

  public removeUnit(unit: Unit): void {
    this.unitService.deleteUnit(unit.id).subscribe(
      resp => {
        console.log("Unit was deleted");
        const index = this.course.units.indexOf(unit, 0);
        if (index > -1) {
          this.course.units.splice(index, 1);
        }
      }
    )
  }

  public removeItem(item: any, list: any[]): void {
    list.splice(list.indexOf(item), 1);
    this.alteredOrder = true;
  }

  public persistOrder(): void {
    let units: Unit[] = this.course.units.map<Unit>(unit => {
      unit.parent = new Course(this.course.id);
      return unit;
    });
    console.log("Modified units");
    console.log(units);
    this.unitService.updateOrderOfUnits(units).subscribe(
      resp => {
        console.log(resp);
        console.log("updated order was successful");
        this.alteredOrder = false;
      },
      err => {
        console.log("error when updating order");
        console.log(err)
      }
    );
  }

  deleteCourse(): void{
    this.courseService.delete(this.course.id).subscribe(
      data => {
        console.log("Delete probably went well");
        this.courseService.removeLoadedCourse(this.course.id);
        this.router.navigate(['/dashboard']);
      },
      err => {
        console.log("Error when making delete.");
      }
    )
  }

  editCourse(): void{
    this.router.navigate(['/edit_course', this.course.id]);
  }

  onFileChanged(event) {
    this.selectedFile = event.target.files[0]
  }

  onUpload() {
    const uploadData = new FormData();
    uploadData.append('image', this.selectedFile, this.selectedFile.name);
    this.http.post('http://localhost:8080/image/upload/' + this.course.id, uploadData)
      .subscribe(
        resp => {
          console.log("upload successful");
        }

      );
    this.selectedFile = null;
    this.cdRef.detectChanges();

  }

  doCourse(): void{
    this.router.navigate(['/local', this.course.id]);
  }

  enroll(): void {
    this.courseService.enrollUser(this.sessionId, this.course.id).subscribe(
      result => {
        if(result){
          this.isEnrolled = true;
          this.status = 0;
        }
      }

    )
  }

  buyCourse(): void {
    this.startsPayment = true;
    this.paymentService.initiatePayment(this.sessionId, this.course.id).subscribe(
      response => {
        if(response.StatusCode == "-1"){
          window.location.href = response.URL_Request;
        }
        else{
          console.log("Todo pago responded with an error.");
          console.log(response);
          this.startsPayment = false;
        }
      },
      err => {
        console.log("Error when initiating payment.");
        this.startsPayment = false;
      }

    )
  }

  unenroll(): void{
    this.courseService.unenrollUser(this.sessionId, this.course.id).subscribe(
      result => {
        if(result){
          this.isEnrolled = false;
        }
      }
    )
  }

  reset(): void{
    this.courseService.resetProgress(this.sessionId, this.course.id).subscribe(
      result => {
        if(result){
          this.status = 0;
        }
      }
    )
  }

  addToProgram(value: string) {
    let selectedProgram = this.programs.find(program => program.name === value);
    if (typeof selectedProgram !== "undefined") {
      this.programService.addCourseToProgram(selectedProgram.id, this.course.id).subscribe(
        result => {
          this.router.navigate(['program_update', selectedProgram.id]);
        }
      );
    }
  }
}
