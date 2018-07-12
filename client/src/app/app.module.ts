import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ShareButtonModule } from '@ngx-share/button';

import { AppComponent } from './app.component';
import {AccessComponent} from "./access/access.component";
import {AppRoutingModule} from "./app-routing.module";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {UserAuthGuardService} from "./services/auth/user-auth-guard.service";
import {AuthService} from "./services/auth/auth.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {HomeComponent} from "./home/home.component";
import {CourseDetailComponent} from "./course/detail/course-detail.component";
import {CourseFormComponent} from "./course/form/course-form.component";
import {UserService} from "./services/user.service";
import {CourseService} from "./services/course.service";
import {ReverseAuthGuardService} from "./services/auth/reverse-auth-guard.service";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LocalCourseComponent} from "./course/local/local-course.component";

import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import {NavbarComponent} from "./navbar/navbar.component";
import {UnitFormComponent} from "./unitForm/unit-form.component";
import {SearchComponent} from "./search/search.component";
import {EditCourseComponent} from "./course/edit/edit-course.component";
import {SearchService} from "./services/search.service";
import {UnitService} from "./services/unit.service";
import {EndbarComponent} from "./endbar/endbar.component";
import {ReviewService} from "./services/review.service";
import {ReviewComponent} from "./course/detail/review/review.component";
import {AdminAuthGuardService} from "./services/auth/admin-auth-guard.service";
import {AdminPanelComponent} from "./admin/panel/admin-panel.component";
import {ProgramService} from "./services/program.service";
import {AdminNavbarComponent} from "./admin/navbar/admin-navbar.component";
import {ProgramUpdateComponent} from "./program/update/program-update.component";
import {ProgramFormComponent} from "./program/form/program-form.component";
import {ProgramDetailComponent} from "./program/detail/program-detail.component";
import {UserAdminAuthGuardService} from "./services/auth/useradmin-auth-guard";

import { MaterialModule } from './material.module';
import {DndListModule} from "ngx-drag-and-drop-lists";
import {MaxLengthPipe} from "./pipes/max-length.pipe";
import {PaymentService} from "./services/payment.service";
import {PaymentComponent} from "./payment/payment.component";
import {RequestInterceptor} from "./services/auth/request.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    AccessComponent,
    DashboardComponent,
    HomeComponent,
    NavbarComponent,
    CourseDetailComponent,
    HomeComponent,
    CourseFormComponent,
    UnitFormComponent,
    EditCourseComponent,
    LocalCourseComponent,
    ReviewComponent,
    SearchComponent,
    EndbarComponent,
    AdminPanelComponent,
    AdminNavbarComponent,
    ProgramUpdateComponent,
    ProgramFormComponent,
    ProgramDetailComponent,
    PaymentComponent,
    MaxLengthPipe
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule,
    DndListModule,
    ShareButtonModule.forRoot()
  ],
  providers: [ReverseAuthGuardService,UserAuthGuardService, AdminAuthGuardService, UserAdminAuthGuardService,
    AuthService, UserService, CourseService, SearchService, UnitService, ReviewService,
    ProgramService, PaymentService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
