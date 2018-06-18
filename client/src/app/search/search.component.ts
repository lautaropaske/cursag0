import {ActivatedRoute, Router} from "@angular/router";
import {SearchService} from "../services/search.service";
import {Component} from "@angular/core";
import {Course} from "../models/Course";

@Component({
  selector : 'search',
  templateUrl : './search.component.html',
  styles: [`
    .star {
      position: relative;  
      display: inline-block;
      font-size: 1rem;
      color: #d3d3d3;
    }
    .full {
      color: red;
    }
    .half {
      position: absolute;
      display: inline-block;
      overflow: hidden;
      color: red;
    }
  `]
})

export class SearchComponent{

  foundCourses: Course[] = [];
  isAdmin: boolean;

  constructor(private router: Router, private service: SearchService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const token = this.route.snapshot.params["token"];
    this.isAdmin = localStorage.getItem("type") == "admin";
    console.log('En search');
    this.service.performSearch(token).subscribe(
      result => {
        this.foundCourses = result;
        console.log("Search succesful.");
        console.log(result);
      },
      err => {
        console.log("Unable to perform search.");
      }
    );
  }
}
