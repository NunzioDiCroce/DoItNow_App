import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, take } from 'rxjs';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { map } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  // - - - - - - - - - - constructor definition - - - - - - - - - -
  constructor(private authSrv: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    // - - - - - - - - - - AuthGuard definition - - - - - - - - - -
    return this.authSrv.user$.pipe(take(1), map((user) => {
      if(user) {
        return true
      }
      alert('Login to access this resource!\nRegister if you have not an account.');
      return this.router.createUrlTree(['/login'])
    }));
  }

}
