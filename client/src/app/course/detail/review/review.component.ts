import {Course} from "../../../models/Course";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";
import {CourseService} from "../../../services/course.service";
import {Component, Input, OnInit} from "@angular/core";
import {ReviewService} from "../../../services/review.service";
import {Review} from "../../../models/Review";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../../../models/User";

@Component({
  selector: 'review',
  template: `
    <div [formGroup]="reviewForm">
      <div class="form-group">
        <label class="center-block">Rating:</label>
        <input class="form-control" formControlName="rating" [(ngModel)]="review.rating">
        <ng-template #t let-fill="fill">
        <span class="star" [class.full]="fill === 100">
          <span class="half" [style.width.%]="fill">&hearts;</span>&hearts;
        </span>
        </ng-template>
        <ngb-rating [(rate)]="review.rating" [starTemplate]="t" [readonly]="false" max="5" class="mr-3" (leave)="hovered=0"></ngb-rating>
      </div>
      <div class="form-group">
        <label class="center-block">Comment:</label>
        <textarea class="form-control" formControlName="textContent" rows="2" [(ngModel)]="review.textContent"></textarea>
      </div>
      <div class="text-center">
        <div style="margin-bottom: 1em">
          <button type="submit" [disabled]="reviewForm.pristine" (click)="postReview();" class="btn btn-primary">Create</button>
        </div>
      </div>
    </div>
    
    <div *ngFor="let review of reviews" class="d-inline-flex">
      
      <ng-template #t let-fill="fill">
              <span class="star" [class.full]="fill === 100">
                <span class="half" [style.width.%]="fill">&hearts;</span>&hearts;
              </span>
      </ng-template>
      <ngb-rating [(rate)]="review.rating" [starTemplate]="t" [readonly]="true" max="5" class="mr-3"></ngb-rating>
      <p>"{{review.textContent}}" <b> - {{review.publisher.name}} {{review.publisher.surname}} </b></p>
      
      

    </div>
  `,
  styles: [`
    .star {
      position: relative;  
      display: inline-block;
      font-size: 1rem;
      color: #d3d3d3;
    }
    .full {
      color: black;
    }
    .half {
      position: absolute;
      display: inline-block;
      overflow: hidden;
      color: black;
    }

    .progress {
      background-color: #aaa;
    }
  `]


})
export class ReviewComponent implements OnInit{


  reviews: Review[] = [];
  @Input() courseId;

  reviewForm : FormGroup;
  review : Review;

  madeReview: boolean;

  ngOnInit(): void {

    this.reviewService.getReviewsOfCourse(this.courseId).subscribe(
      reviews => {
        console.log("course id is " + this.courseId);
        console.log("reviews where found successfully.");
        console.log(reviews);
        this.reviews = reviews;
        let personalReview =reviews.find(review => this.review.publisher.id == +localStorage.getItem("id"));
        if(personalReview){
          this.review = personalReview;
          this.madeReview = true;
        }

      },
      err => {
        console.log("Unable to get reviews from database.");
        this.reviews = null;
      }
    );

    this.createReviewForm();
    this.review = Review.create_empty();
    this.review.publisher = User.create_empty();
    this.review.publisher.id = +localStorage.getItem("id");
    this.review.publisher.name = localStorage.getItem("name");
    this.review.publisher.surname = localStorage.getItem("surname");

    this.review.reviewed = Course.create_empty();
    this.review.reviewed.id = this.courseId;

  }


  constructor(private router: Router, private reviewService: ReviewService, private fb : FormBuilder) {
  }

  createReviewForm() {
    this.reviewForm = this.fb.group({
      rating : [ , Validators.required],
      textContent : ['']
    });
  }

  postReview() : void {
    debugger;
    this.reviewService.addReview(this.review).subscribe(
      review => {
        console.log("Review was successfully created");
        console.log(review);
        this.reviews.push(review);
        this.review = review;
        this.madeReview = true;
      },
      err => {
        console.log("Review could not be created");
      }
    );
  }


}
