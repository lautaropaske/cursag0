import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "./user.service";

@Component({
  selector: 'profile',
  template: `
    <h1>Welcome {{name}} {{surname}} (id: {{id}})</h1>
    <button type="button" (click)="logout();" class="btn btn-primary">Log out</button>

  `
})
export class ProfileComponent {

  id = localStorage.getItem("id");
  name = localStorage.getItem("name");
  surname = localStorage.getItem("surname");

  constructor(private router: Router) {}


  logout():void{
    localStorage.clear();
    this.router.navigate(['access']);

  }

}
