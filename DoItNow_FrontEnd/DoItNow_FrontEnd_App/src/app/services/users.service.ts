import { Injectable } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, map, of } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient, private router: Router) { }

  // getUsers
  getUsers(page: number, size: number, sort: string): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null); // to return an empty observable
    } else {
      const user = JSON.parse(userString);
      const params = new HttpParams().set('page', page.toString()).set('size', size.toString()).set('sortBy', sort);
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.get<any>('http://localhost:3001/users', {params, headers}).pipe(map(response => {
        return {content: response.content, totalElements: response.totalElements, totalPages: Math.ceil(response.totalElements/size)}
      }));
    }
  }

  // getUserDetails
  getUserDetails(userId: string): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null); // to return an empty observable
    } else {
      const user = JSON.parse(userString);
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.get<any>(`http://localhost:3001/users/${userId}`, {headers});
    }
  }

}
