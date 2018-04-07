import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {AccessComponent} from "./access.component";
import {AppRoutingModule} from "./app-routing.module";
import {ProfileComponent} from "./profile.component";
import {AuthGuardService} from "./auth/auth-guard.service";
import {AuthService} from "./auth/auth.service";


@NgModule({
  declarations: [
    AppComponent,
    AccessComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [AuthGuardService, AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
