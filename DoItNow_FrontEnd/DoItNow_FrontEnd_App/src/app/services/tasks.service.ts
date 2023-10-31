import { Injectable } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { map } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class TasksService {

  // - - - - - - - - - - TasksService definition - - - - - - - - - -
  constructor(private httpClient: HttpClient) { }

  getTasks(page: number, size: number, sortBy: string) {
    const userString = localStorage.getItem('user');
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString()).set('sortBy', sortBy);
    if(!userString) {
      return this.httpClient.get<any>('http://localhost:3001/tasks', {params});
    }
    const user = JSON.parse(userString);
    const token = user.accessToken;
    const headers = new HttpHeaders({Authorization: `Bearer {token}`});
    return this.httpClient.get<any>('http://localhost:3001/tasks', {params, headers}).pipe(map(response => {
      return {
        content: response.content, totalElements: response.totalElements, totalPages: Math.ceil(response.totalElements/size)
      }
    }));
  }

}
