import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { UsersService } from 'src/app/services/users.service';
import { User } from 'src/app/models/user.interface';
import { ActivatedRoute, Router } from '@angular/router'; // ActivatedRoute to get task id from url

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit, OnDestroy {

  // - - - - - - - - - - UserDetailsComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  loadUserDetailsSub: Subscription | undefined;
  userDetails: User | undefined;

  constructor(private authSrv: AuthService, private usersSrv: UsersService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });

    // ActivatedRoute to get user id from url
    this.route.params.subscribe((_params) => {
      const userId = _params['id'];
      this.loadUserDetails(userId);
    });
  }

  // loadTaskDetails
  loadUserDetails(userId: string): void {
    this.loadUserDetailsSub = this.usersSrv.getUserDetails(userId).subscribe((_userDetails: User) => {
      this.userDetails = _userDetails;
    });
  }

  // goToUsers
  goToUsers(): void {
    this.router.navigate(['/users']);
  }

  ngOnDestroy(): void {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
    if(this.loadUserDetailsSub) {
      this.loadUserDetailsSub.unsubscribe();
    }
  }

}
