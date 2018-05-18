import {Injectable} from "@angular/core";

@Injectable()
export class AuthService {

  public userIsAuthenticated(): boolean {

    const token = localStorage.getItem('token');
    const type = localStorage.getItem('type');


    return token == "cursago" && type == "user";

  }

  public adminIsAuthenticated(): boolean {

    const token = localStorage.getItem('token');
    const type = localStorage.getItem('type');


    return token == "cursago" && type == "admin";

  }
}
