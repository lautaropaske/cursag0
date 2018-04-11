import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from "./User";
import {Observable} from "rxjs/Observable";


const URL ="http://localhost:8080";

// const httpOptions = {
//   headers: new HttpHeaders({
//     'Content-Type':  'application/json',
//     'Authorization': 'my-auth-token'
//   })
// };

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(email: string, password: string): Observable<User> {
    return this.http.get<User>(URL+'/log?mail=' + email+'&pass=' + password);
  }

   addUser (user: User): Observable<User>{
     return this.http.post<User>(URL+'user', user);
   }
}
