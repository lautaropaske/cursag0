import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AccessComponent} from "./access.component";
import {AuthGuardService as AuthGuard} from "./auth/auth-guard.service";
import {ProfileComponent} from "./profile.component";
import {HomeComponent} from "./home.component";


const routes: Routes = [
  { path: '', component: AccessComponent },
  { path: 'access',  component:AccessComponent },
  { path: 'home',  component:HomeComponent },

  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard]
  },
  { path: '**', redirectTo: '' }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}