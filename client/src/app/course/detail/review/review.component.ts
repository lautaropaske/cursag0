import {Course} from "../../../models/Course";
import {NgbCarouselConfig} from "@ng-bootstrap/ng-bootstrap";
import {CourseService} from "../../../services/course.service";
import {Component, Input, OnInit} from "@angular/core";
import {ReviewService} from "../../../services/review.service";
import {Review} from "../../../models/Review";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../../../models/User";
import {ReviewResp} from "../../../models/ReviewResp";

@Component({
  selector: 'review',
  template: `
    <div [formGroup]="reviewForm">
      <div class="form-group" style=" margin-bottom: 0.2rem">
        <label class="center-block">Rating:</label>
        <ng-template #t let-fill="fill">
        <span class="star" [class.full]="fill === 100">
          <span class="half" [style.width.%]="fill">&hearts;</span>&hearts;
        </span>
        </ng-template>
        <ngb-rating [(rate)]="review.rating" [starTemplate]="t" [readonly]="false" max="5" class="ml-3" (leave)="hovered=0"></ngb-rating>
      </div>
      <div class="form-group">
        <label class="center-block">Comment:</label>
        <textarea class="form-control" formControlName="textContent" rows="2" [(ngModel)]="review.textContent"></textarea>
      </div>
      <div class="text-center">
        <div style="margin-bottom: 1em">
          <div *ngIf="!madeReview">
            <button type="submit" [disabled]="reviewForm.pristine" (click)="postReview();" class="btn btn-primary">Submit review</button>
          </div>
          <div *ngIf="madeReview">
            <button type="button" (click)="updateReview();" class="btn btn-primary">Edit review</button>
          </div>
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
      font-size: 1.2rem;
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


  reviews: ReviewResp[] = [];
  @Input() courseId;

  reviewForm : FormGroup;
  review : Review;

  madeReview: boolean;


  ngOnInit(): void {

    this.reviewService.getReviewsOfCourse(this.courseId).subscribe(
      reviewsResp => {
        console.log("course id is " + this.courseId);
        console.log("reviews where found successfully.");
        console.log(reviewsResp);
        this.reviews = reviewsResp;
        let personalReview = reviewsResp.find(aReview => aReview.publisher.id === +localStorage.getItem("id"));
        console.log("personal review: ");
        console.log(personalReview);
        if(personalReview != undefined){
          let course: Course = Course.create_empty();
          course.id = this.courseId;
          this.review = new Review(personalReview.id, personalReview.textContent, personalReview.rating,
                          personalReview.publisher, course);

          console.log("The current review selected is " );
          console.log(this.review );
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
    this.reviewService.addReview(this.review).subscribe(
      review => {
        console.log("Review was successfully created");
        console.log(review);
        this.reviews.push(new ReviewResp(review.id,review.textContent, review.rating, review.publisher));
        this.review = review;
        this.madeReview = true;
      },
      err => {
        console.log("Review could not be created");
      }
    );
  }

  updateReview() : void {
    this.reviewService.updateReview(this.review).subscribe(
      review => {
        console.log("Review was successfully updated");
        console.log(review);
        this.reviews.splice(this.reviews.indexOf(this.reviews.find(review => this.review.publisher.id == +localStorage.getItem("id")))
          ,1);
        this.reviews.push(review);
      },
      err => {
        console.log("Review could not be updated");
      }
    );
  }


}
