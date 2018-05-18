import {Router} from "@angular/router";
import {Component, Input, OnInit} from "@angular/core";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'adminpanel',
  templateUrl : './admin-panel.component.html'
})
export class AdminPanelComponent implements OnInit{

  ngOnInit(): void {

  }

  constructor(private router: Router, private fb : FormBuilder) {
  }

  logout():void{
    localStorage.clear();
    this.router.navigateByUrl('/search', {skipLocationChange: true}).then(()=>
      this.router.navigate(['home']));
  }

}
