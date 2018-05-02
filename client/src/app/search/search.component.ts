import {ActivatedRoute, Router} from "@angular/router";
import {SearchService} from "../services/search.service";
import {Component} from "@angular/core";
import {Course} from "../models/Course";

@Component({
  selector : 'search',
  templateUrl : './search.component.html'
})

export class SearchComponent{

  foundCourses: Course[] = [];

  constructor(private router: Router, private service: SearchService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const token = this.route.snapshot.params["token"];
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
