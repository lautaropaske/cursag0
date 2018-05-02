import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuardService as AuthGuard} from "./services/auth/auth-guard.service";
import {ReverseAuthGuardService as ReverseAuthGuard} from "./services/auth/reverse-auth-guard.service";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {HomeComponent} from "./home/home.component";
import {CourseFormComponent} from "./course/form/course-form.component";
import {CourseDetailComponent} from "./course/detail/course-detail.component";
import {AccessComponent} from "./access/access.component";
import {LocalCourseComponent} from "./course/local/local-course.component";
import {UnitFormComponent} from "./unitForm/unit-form.component";
import {SearchComponent} from "./search/search.component";
import {EditCourseComponent} from "./course/edit/edit-course.component";


const routes: Routes = [
  { path: 'home',  component:HomeComponent },
  { path: 'access',  component:AccessComponent, canActivate: [ReverseAuthGuard]},

  { path: 'create_course',  component:CourseFormComponent, canActivate: [AuthGuard] },
  { path: "details/:id", component: CourseDetailComponent,canActivate: [AuthGuard] },
  { path: "local/:id", component: LocalCourseComponent,canActivate: [AuthGuard] },
  { path: "create_unit/:id", component: UnitFormComponent,canActivate: [AuthGuard] },
  { path: "edit_course/:id", component: EditCourseComponent,canActivate: [AuthGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  { path: 'search/:token', component: SearchComponent},
  { path: '**', redirectTo: 'home' }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
