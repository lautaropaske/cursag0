import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FormBuilder} from "@angular/forms";


@Component({
  selector: 'admin-navbar',
  template: `
    <nav class="navbar sticky-top navbar-dark bg-primary d-flex justify-content-between">
      <a class="navbar-brand" [routerLink]="['/dashboard']">Cursago</a>
      <form class="form-inline">
        <input #token class="form-control mr-sm-2" type="search" placeholder="Look up our courses!" aria-label="Search">
        <button type="button" (click)="search(token.value);" class="btn btn-outline-light my-2 my-sm-0">Search</button>
      </form>
      <div class="d-inline-flex">
        <button type="button" (click)="createProgram();" class="btn btn-outline-light my-2 my-sm-0">Create Program</button>
        <a class="nav-item nav-link" (click)="logout();">Logout</a>
        
      </div>
    </nav>
  `
})
export class AdminNavbarComponent implements OnInit{

  ngOnInit(): void {

  }

  constructor(private router: Router) {
  }

  search(token: String) : void{
    this.router.navigateByUrl('/search', {skipLocationChange: true}).then(()=>
      this.router.navigate(["search",token]));
  }

  createProgram(): void{
    this.router.navigate(['create_program']);
  }

  logout():void{
    localStorage.clear();
    this.router.navigateByUrl('/search', {skipLocationChange: true}).then(()=>
      this.router.navigate(['home']));
  }

}
