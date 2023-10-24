import { Component, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthService } from '../auth.service';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  // - - - - - - - - - - RegisterComponent definition - - - - - - - - - -
  constructor(private authSrv: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  register(form: NgForm) {
    this.authSrv.register(form.value).subscribe(() => {
      window.alert('Registration success!');
      this.router.navigate(['/login']);
    },
    (error) => {
      if(error.error && error.error.errorsList && error.error.errorsList.length > 0) {
        let errorsMessage: string = 'Registration failed. Errors:';
        error.error.errorsList.forEach((errorMessage: string) => {
          errorsMessage += `\n - ${errorMessage}`
        });
        window.alert(errorsMessage);
      } else {
        window.alert('An error occurred while processing your request.');
      }
    });
  }

}
