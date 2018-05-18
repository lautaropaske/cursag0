import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from './auth.service';


@Injectable()
export class ReverseAuthGuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {}


  canActivate(): boolean {
    if (this.auth.userIsAuthenticated()) {
      this.router.navigate(['dashboard']);

      console.log("User is authenticated and has been redirected");
      return false;
    }
    else if(this.auth.adminIsAuthenticated()){
      this.router.navigate(['adminpanel']);

      console.log("Admin is authenticated and has been redirected");
      return false;
    }

    return true;
  }
}
