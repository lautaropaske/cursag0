import {FormBuilder, FormGroup} from "@angular/forms";
import {ReviewResp} from "../models/ReviewResp";
import {Component, Input, OnInit} from "@angular/core";
import {Review} from "../models/Review";
import {ReviewService} from "../services/review.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PaymentService} from "../services/payment.service";

@Component({
  selector: 'payment',
  template: `
    <navbar></navbar>
    <div class="container">
      <div class="d-flex d-inline-flex align-items-center">
        <i *ngIf="loading" style="margin-top: 50px; font-size: 4em" class="fa fa-spinner fa-spin"></i>
        <i *ngIf="!loading && result" style="margin-top: 50px; color:green; font-size: 4em" class="far fa-check-circle"></i>
        <i *ngIf="!loading && !result" style="margin-top: 50px; color:red; font-size: 4em" class="far fa-times-circle"></i>

        <h1 *ngIf="loading" class="mt-5 ml-5">Your payment is currently being verified</h1>
        <h1 *ngIf="!loading && result" class="mt-5 ml-5">Your payment was successfull!</h1>
        <h1 *ngIf="!loading && !result" class="mt-5 ml-5">An error has ocurred when making the payment</h1>
      </div>
      <div class="row">
        <button type="button" class="btn mt-2 btn-success mt-4" [routerLink]="['/course', courseId]" *ngIf="!loading && result">Go to course!</button>
  
        <h4 class="mt-4" *ngIf="!loading && !result">We recieved the following response:</h4>
        <p *ngIf="!loading && !result">"{{errorMessage}}"</p>
      </div>
    </div>
    <endbar></endbar>
  `,
  styles: [`
  `]


})
export class PaymentComponent implements OnInit{

  loading: boolean;
  result: boolean;
  courseId: number;
  errorMessage: string;

  constructor(private router: Router,private route: ActivatedRoute, private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.loading = true;
    this.courseId = +this.route.snapshot.params["id"];

    this.paymentService.verifyPayment(+localStorage.getItem("id"),this.courseId, this.getParam("Answer")).subscribe(
      resp => {
        console.log("Todo pago has responded correctly");
        console.log(resp);
        if(resp.StatusCode === "-1" && resp.StatusMessage === "APROBADA"){
          this.loading = false;
          this.result = true;
        }
        else{
          this.loading = false;
          this.result = false;
          this.errorMessage = resp.StatusMessage
        }


      },
      err => {
        console.log("An error has ocurred")
      }
    )
  }

  getParam(name){
    const results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if(!results){
      return '';
    }
    return results[1];
  }

}


