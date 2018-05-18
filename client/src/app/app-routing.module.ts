import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserAuthGuardService as UserAuthGuard} from "./services/auth/user-auth-guard.service";
import {AdminAuthGuardService as AdminAuthGuard} from "./services/auth/admin-auth-guard.service";
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
import {AdminPanelComponent} from "./adminpanel/admin-panel.component";


const routes: Routes = [
  { path: 'home',  component:HomeComponent },
  { path: 'access',  component:AccessComponent, canActivate: [ReverseAuthGuard]},

  { path: 'adminpanel',  component:AdminPanelComponent, canActivate: [AdminAuthGuard] },

  { path: 'create_course',  component:CourseFormComponent, canActivate: [UserAuthGuard] },
  { path: "details/:id", component: CourseDetailComponent,canActivate: [UserAuthGuard] },
  { path: "local/:courseId/:progress", component: LocalCourseComponent,canActivate: [UserAuthGuard] },
  { path: "create_unit/:id", component: UnitFormComponent,canActivate: [UserAuthGuard] },
  { path: "edit_course/:id", component: EditCourseComponent,canActivate: [UserAuthGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [UserAuthGuard]},
  { path: 'search/:token', component: SearchComponent},
  { path: '**', redirectTo: 'home' }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
