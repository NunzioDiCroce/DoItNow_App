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
        switch(error.error) {
          case 'The email has already been used.':
          window.alert('The email has already been used.');
          break
        }
      });
  }

}
