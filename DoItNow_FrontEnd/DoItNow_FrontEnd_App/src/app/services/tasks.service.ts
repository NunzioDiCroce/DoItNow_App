import { Injectable } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { map, of } from 'rxjs';
import { Router } from '@angular/router';
import { TaskCreate } from '../models/task-create.interface';


@Injectable({
  providedIn: 'root'
})
export class TasksService {

  // - - - - - - - - - - TasksService definition - - - - - - - - - -
  constructor(private httpClient: HttpClient, private router: Router) { }

  // getTasks
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

  // createTask
  createTask(task: TaskCreate) {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null) // to return an empty observable
    }
    const user = JSON.parse(userString);
    const token = user.accessToken;
    const headers = new HttpHeaders({Authorization: `Bearer {token}`});
    return this.httpClient.post<any>('http://localhost:3001/tasks', task, {headers});
  }


}
