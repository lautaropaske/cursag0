import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Review} from "../../../../models/Review";
import {ReviewService} from "../../../../services/review.service";
import {Course} from "../../../../models/Course";
import {User} from "../../../../models/User";

@Component({
  selector : 'review-form',
  templateUrl : './review-form.component.html',
  styles: [`
    .star {
      position: relative;
      display: inline-block;
      font-size: 3rem;
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
  `]
})

export class ReviewFormComponent{

  reviewForm : FormGroup;
  review : Review;
  @Input() courseId;

  constructor(private router: Router, private reviewService: ReviewService, private fb : FormBuilder) {
    this.createReviewForm();
    this.review = Review.create_empty();
    this.review.publisher = User.create_empty();
    this.review.publisher.id = +localStorage.getItem("id");
    this.review.reviewed = Course.create_empty();
    this.review.reviewed.id = this.courseId;
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
      },
      err => {
        console.log("Review could not be created");
      }
    );
  }
}
