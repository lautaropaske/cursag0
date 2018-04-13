import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "./user.service";

@Component({
  selector: 'profile',
  template: `
    <nav class="navbar navbar-light bg-light">
      <a class="navbar-brand">Cursago</a>
      <form class="form-inline">
        <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
      </form>
      <a class="nav-item nav-link" [routerLink]="" (click)="logout();">Logout</a>
    </nav>
    <div class="container mt-5">
        <h4 >Currently logged in to {{name}} {{surname}} (id: {{id}})</h4>

        <button type="button" (click)="createCourse();" class="btn btn-primary">Add new course</button>
    </div> 
  `
})
export class ProfileComponent {

  id = localStorage.getItem("id");
  name = localStorage.getItem("name");
  surname = localStorage.getItem("surname");

  constructor(private router: Router) {}

  createCourse(): void{
    this.router.navigate(['create_course']);

  }

  logout():void{
    localStorage.clear();
    this.router.navigate(['access']);
  }



}
