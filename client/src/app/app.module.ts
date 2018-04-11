import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {AccessComponent} from "./access.component";
import {AppRoutingModule} from "./app-routing.module";
import {ProfileComponent} from "./profile.component";
import {AuthGuardService} from "./auth/auth-guard.service";
import {AuthService} from "./auth/auth.service";
import {HttpClientModule} from "@angular/common/http";
import {UserService} from "./user.service";
import {CourseService} from "./course.service";
import {HomeComponent} from "./home.component";


@NgModule({
  declarations: [
    AppComponent,
    AccessComponent,
    ProfileComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [AuthGuardService, AuthService, UserService, CourseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
