import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from './auth.service';


@Injectable()
export class UserAuthGuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {}


  canActivate(): boolean {
    if (!this.auth.userIsAuthenticated()) {
      this.router.navigate(['access']);

      console.log("User has not been authenticated and has been redirected");
      return false;
    }
    return true;
  }
}
