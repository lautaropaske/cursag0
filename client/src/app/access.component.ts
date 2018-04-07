import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'access',
  templateUrl: './access.component.html'
  // styleUrls: ['./app.component.css']
})

export class AccessComponent {

  constructor(private router: Router) {}


  login(email: string, password: string): void {
    localStorage.setItem("token","cursago");
    this.router.navigate(['profile']);

    console.log("Va a ser login: "+email+", " + password);

    //TODO que verifique con la API si mail y pass coinciden
  }

  signup(name: string, surname: string, email: string, password: string): void {
    console.log("Signup: "+ name+", " + surname +", "+email+", " + password );

    //TODO crear una cuenta
  }
}
