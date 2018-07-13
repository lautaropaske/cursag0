import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserAuthGuardService as UserAuthGuard} from "./services/auth/user-auth-guard.service";
import {AdminAuthGuardService as AdminAuthGuard} from "./services/auth/admin-auth-guard.service";
import {UserAdminAuthGuardService as UserAdminAuthGuard} from "./services/auth/useradmin-auth-guard";
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
import {AdminPanelComponent} from "./admin/panel/admin-panel.component";
import {ProgramFormComponent} from "./program/form/program-form.component";
import {ProgramUpdateComponent} from "./program/update/program-update.component";
import {ProgramDetailComponent} from "./program/detail/program-detail.component";
import {PaymentComponent} from "./payment/payment.component";


const routes: Routes = [
  { path: 'home',  component:HomeComponent },
  { path: 'access',  component:AccessComponent, canActivate: [ReverseAuthGuard]},

  { path: 'adminpanel',  component:AdminPanelComponent, canActivate: [AdminAuthGuard] },
  { path: 'create_program',  component:ProgramFormComponent, canActivate: [AdminAuthGuard] },
  { path: "program_update/:id", component: ProgramUpdateComponent, canActivate: [AdminAuthGuard] },

  { path: "course/:id", component: CourseDetailComponent,canActivate: [UserAdminAuthGuard] },

  { path: "program_detail/:id", component: ProgramDetailComponent, canActivate: [UserAuthGuard] },
  { path: 'create_course',  component:CourseFormComponent, canActivate: [UserAuthGuard] },
  { path: "local/:courseId", component: LocalCourseComponent,canActivate: [UserAuthGuard] },
  { path: "create_unit/:id", component: UnitFormComponent,canActivate: [UserAuthGuard] },
  { path: "edit_course/:id", component: EditCourseComponent,canActivate: [UserAuthGuard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [UserAuthGuard]},
  { path: "payment/:id", component: PaymentComponent, canActivate: [UserAuthGuard] },


  { path: 'search/:token', component: SearchComponent},
  { path: '**', redirectTo: 'home' }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
