import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
import {User} from "../models/User";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ExtCourse} from "../models/ExtCourse";


@Component({
  selector: 'access',
  templateUrl: './access.component.html'
})

export class AccessComponent implements OnInit{

  loginErrorMessage: string;
  displayLoginError: boolean;

  signupErrorMessage: string;
  displaySignupError: boolean;

  userForm : FormGroup;
  user: User;

  loginForm : FormGroup;
  mail: string;
  password: string;

  constructor(private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    this.createUserForm();
    this.createLoginForm();
    this.user = User.create_empty();
    this.mail = '';
    this.password = '';
  }

  createUserForm() {
    this.userForm = new FormGroup({
      name : new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      mail: new FormControl('', [
        Validators.required,
        Validators.pattern("[^ @]*@[^ @]*")
      ]),
      password: new FormControl('', [
        Validators.minLength(8),
        Validators.required
      ])
    });
  }

  createLoginForm() {
    this.loginForm = new FormGroup({
      mail: new FormControl('', [
        Validators.required,
        Validators.pattern("[^ @]*@[^ @]*")
      ]),
      password: new FormControl('', Validators.required)
    });

  }


  login(): void {
    if(this.loginForm.valid) {
      this.userService.getUser(this.mail, this.password).subscribe(
        user => {

          console.log("User was found in database, and has been logged in");
          console.log(user);
          this.redirectValidUser(user);
        },
        err => {
          this.loginErrorMessage = err.error;
          this.displayLoginError = true;
        }
      );
    }
    else{
      this.loginErrorMessage = 'Please verify your values';
      this.displayLoginError = true;
    }
  }

  signup(): void {
    if(this.userForm.valid) {
      console.log(this.user);
      this.userService.addUser(this.user).subscribe(
        user => {
          console.log("User was successfully created, and has been logged in");
          console.log(user);
          this.redirectValidUser(user);
        },
        err => {
          this.signupErrorMessage = err.error;
          this.displaySignupError = true;
        }
      );
    }
    else{
      this.signupErrorMessage = 'Please verify your values';
      this.displaySignupError = true;
    }
  }

  redirectValidUser(user: User): void{
    localStorage.setItem("token","cursago");
    localStorage.setItem("id", ''+user.id);
    localStorage.setItem("name", user.name);
    localStorage.setItem("surname", user.surname);
    localStorage.setItem("encodedUser", btoa(user.mail +':'+user.password));
    if(user.isAdmin){
      localStorage.setItem("type", "admin");
      this.router.navigate(['adminpanel']);
    }
    else{
      localStorage.setItem("type", "user");
      this.router.navigate(['dashboard']);
    }

  }

}
