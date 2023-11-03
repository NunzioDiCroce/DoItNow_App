import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthService } from 'src/app/auth/auth.service';
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy { // add OnDestroy

  // - - - - - - - - - - NavbarComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;

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

  ngOnDestroy(): void {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
  }

}
