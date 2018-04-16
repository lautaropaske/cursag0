import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
import {User} from "../models/User";


@Component({
  selector: 'access',
  templateUrl: './access.component.html'
  // styleUrls: ['./app.component.css']
})

export class AccessComponent {

  constructor(private router: Router, private userService: UserService) {}


  login(email: string, password: string): void {
    this.userService.getUser(email, password).subscribe(
      user => {
        console.log("User was found in database, and has been logged in");
        console.log(user);
        this.redirectValidUser(user);
      },
      err => {
        console.log("User is not in the database.")
      }
    );
  }

  signup(name: string, surname: string, email: string, password: string): void {

    this.userService.addUser(new User(null,email, password,name, surname)).subscribe(
      user => {
        console.log("User was successfully created, and has been logged in");
        console.log(user);
        this.redirectValidUser(user);
      },
      err => {
        console.log("error ocurred in post of signup");
      }
    );
  }

  redirectValidUser(user: User): void{
    localStorage.setItem("token","cursago");
    localStorage.setItem("id", '' + user.id);
    localStorage.setItem("name", '' + user.name);
    localStorage.setItem("surname", '' + user.surname);
    this.router.navigate(['dashboard']);
  }
}
