import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {AuthService} from "./auth.service";


@Injectable()
export class UserAdminAuthGuardService implements CanActivate {

  constructor(public auth: AuthService, public router: Router) {}


  canActivate(): boolean {
    if (!this.auth.adminIsAuthenticated() && !this.auth.userIsAuthenticated()) {
      this.router.navigate(['access']);

      console.log("Neither admin or user has not been authenticated and has been redirected");
      return false;
    }
    return true;
  }
}
