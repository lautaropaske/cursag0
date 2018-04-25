import {CourseService} from "../services/course.service";
import {Router} from "@angular/router";
import {Component} from "@angular/core";

@Component({
  selector: 'navbar',
  templateUrl : './navbar.component.html'
})
export class NavbarComponent{

  constructor(private router: Router,private courseService: CourseService) {}

  logout():void{
    localStorage.clear();
    this.router.navigate(['access']);
  }

  search(token: String) : void{
    this.router.navigate(['/search',token]);
  }
}
