import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let encodedUser = localStorage.getItem('encodedUser');

    request = request.clone({
      setHeaders: {
        Authorization: `Basic ${encodedUser}`
      }
    });
    return next.handle(request);
  }
}
