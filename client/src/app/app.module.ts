import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {AccessComponent} from "./access/access.component";
import {AppRoutingModule} from "./app-routing.module";
import {ProfileComponent} from "./profile.component";
import {AuthGuardService} from "./services/auth/auth-guard.service";
import {AuthService} from "./services/auth/auth.service";
import {HttpClientModule} from "@angular/common/http";
import {HomeComponent} from "./home.component";
import {CourseDetailComponent} from "./course/detail/course-detail.component";
import {CourseFormComponent} from "./course/form/course-form.component";
import {UserService} from "./services/user.service";
import {CourseService} from "./services/course.service";
import {ReverseAuthGuardService} from "./services/auth/reverse-auth-guard.service";


@NgModule({
  declarations: [
    AppComponent,
    AccessComponent,
    ProfileComponent,
    HomeComponent,
    CourseDetailComponent,
    HomeComponent,
    CourseFormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [ReverseAuthGuardService,AuthGuardService,
    AuthService, UserService, CourseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
