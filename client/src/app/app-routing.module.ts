import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuardService as AuthGuard} from "./services/auth/auth-guard.service";
import {ReverseAuthGuardService as ReverseAuthGuard} from "./services/auth/reverse-auth-guard.service";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {HomeComponent} from "./home.component";
import {CourseFormComponent} from "./course/form/course-form.component";
import {CourseDetailComponent} from "./course/detail/course-detail.component";
import {AccessComponent} from "./access/access.component";


const routes: Routes = [
  { path: 'home',  component:HomeComponent },
  { path: 'access',  component:AccessComponent, canActivate: [ReverseAuthGuard]},

  { path: 'create_course',  component:CourseFormComponent, canActivate: [AuthGuard] },
  { path: "details/:id", component: CourseDetailComponent,canActivate: [AuthGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  { path: '**', redirectTo: 'access' }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
