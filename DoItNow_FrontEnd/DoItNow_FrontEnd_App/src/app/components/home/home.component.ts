import { Component, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { Router } from '@angular/router';
import { AuthData } from 'src/app/auth/auth-data.interface';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  // - - - - - - - - - - HomeComponent definition - - - - - - - - - -
  user!: AuthData | null;

  constructor(private authSrv: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });
  }

  goToRegister() {
    this.router.navigate(['register']);
  }

  goToLogin() {
    this.router.navigate(['login']);
  }

}
