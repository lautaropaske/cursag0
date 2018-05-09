import {Router} from "@angular/router";
import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'endbar',
  templateUrl : './endbar.component.html'
})

export class EndbarComponent implements OnInit{
  constructor(private router: Router) {}

  ngOnInit(): void {
  }
}
