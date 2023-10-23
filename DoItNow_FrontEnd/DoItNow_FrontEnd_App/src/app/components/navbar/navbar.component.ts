import { Component, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/auth/auth-data.interface';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  // - - - - - - - - - - NavbarComponent definition - - - - - - - - - -
  user!: AuthData | null;

  constructor(private authSrv: AuthService) { }

  ngOnInit(): void {
    this.authSrv.user$.subscribe((user) => {
      this.user = user;
      console.log(this.user);
    });
  }

  logout() {
    this.authSrv.logout();
  }

}
