import {ActivatedRoute, Router} from "@angular/router";
import {SearchService} from "../services/search.service";
import {Component} from "@angular/core";

@Component({
  selector : 'search',
  template : './search.component.html'
})

export class SearchComponent{

  constructor(private router: Router, private service: SearchService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const token = this.route.snapshot.params["token"];
    // const sessionId = +localStorage.getItem("id");

    this.service.performSearch(token).subscribe(
      result => {
        console.log("Search succesful.");
        console.log(result);
      },
      err => {
        console.log("Unable to perform search.");
      }
    );
  }
}
