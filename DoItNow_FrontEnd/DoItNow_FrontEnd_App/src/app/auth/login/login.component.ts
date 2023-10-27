import { Component, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  // - - - - - - - - - - LoginComponent definition - - - - - - - - - -
  isLoading = false;

  constructor(private authSrv: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  login(form: NgForm) {
    this.isLoading = true;
    try {
      this.authSrv.login(form.value).subscribe(() => {
        this.isLoading = false;
        alert('Registration success!');
        this.router.navigate(['/']);
      },
      (error) => {
        this.isLoading = false;
        alert('Wrong login!');
        console.log(error);
      });
    } catch(error) {

    }
  }

}
