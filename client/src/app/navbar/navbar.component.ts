import {CourseService} from "../services/course.service";
import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'navbar',
  templateUrl : './navbar.component.html'
})

export class NavbarComponent implements OnInit{

  loggedIn: boolean;

  constructor(private router: Router,private courseService: CourseService) {}

  ngOnInit(): void {
    if(localStorage.getItem('token') == 'cursago'){
      this.loggedIn = true;
    }
    else {
      this.loggedIn = false;
    }
  }

  logout():void{
    localStorage.clear();
    this.router.navigate(['home']);
  }

  search(token: String) : void{
    this.router.navigateByUrl('/search', {skipLocationChange: true}).then(()=>
      this.router.navigate(["search",token]));
  }

  access(): void{
    this.router.navigate(['access']);

  }
}
