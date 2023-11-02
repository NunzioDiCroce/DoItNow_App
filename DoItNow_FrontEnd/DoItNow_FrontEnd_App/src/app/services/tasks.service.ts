import { Injectable } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { HttpClient, HttpHeaderResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { TaskCreate } from '../models/task-create.interface';
import { TaskUpdate } from '../models/task-update.interface';


@Injectable({
  providedIn: 'root'
})
export class TasksService {

  // - - - - - - - - - - TasksService definition - - - - - - - - - -
  constructor(private httpClient: HttpClient, private router: Router) { }

  // private method to get token and create headers
  private createHeaders(): HttpHeaders {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return new HttpHeaders();
    }
    const user = JSON.parse(userString);
    const token = user.accessToken;
    return new HttpHeaders({Authorization: `Bearer ${token}`});
  }

  // getTasks
  getTasks(page: number, size: number, sortBy: string): Observable<any> {
    const headers = this.createHeaders();
    if(headers.keys.length === 0) {
      return of(null);
    // 'HttpHeaders.keys' is an Iterator object of headers. 'length === 0' means no headers so no authenticated user.
    }
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString()).set('sortBy', sortBy);
    return this.httpClient.get<any>('http://localhost:3001/tasks', {headers, params}).pipe(map(response => {
      return {content: response.content, totalElements: response.totalElements, totalPages: Math.ceil(response.totalElements/size)
      }
    }));
  }

  // createTask
  createTask(task: TaskCreate): Observable<any> {
    const headers = this.createHeaders();
    if(headers.keys.length === 0) {
      return of(null);
    // 'HttpHeaders.keys' is an Iterator object of headers. 'length === 0' means no headers so no authenticated user.
    }
    return this.httpClient.post<any>('http://localhost:3001/tasks', task, {headers});
  }

  // getTaskDetails
  getTaskDetails(taskId: string): Observable<any> {
    const headers = this.createHeaders();
    if(headers.keys.length === 0) {
      return of(null);
    // 'HttpHeaders.keys' is an Iterator object of headers. 'length === 0' means no headers so no authenticated user.
    }
    return this.httpClient.get<any>(`http://localhost:3001/tasks/${taskId}`, {headers});
  }

  // updateTask
  updateTask(taskId: string, task: TaskUpdate): Observable<any> {
    const headers = this.createHeaders();
    if(headers.keys.length === 0) {
      return of(null);
    // 'HttpHeaders.keys' is an Iterator object of headers. 'length === 0' means no headers so no authenticated user.
    }
    return this.httpClient.put<any>(`http://localhost:3001/tasks/${taskId}`, task, {headers});
  }

  // completeTask
  completeTask(taskId: string, completedTask: boolean): Observable<any> {
    const headers = this.createHeaders();
    if(headers.keys.length === 0) {
      return of(null);
    }
    return this.httpClient.put<any>(`http://localhost:3001/tasks/${taskId}`, completedTask, {headers});
  }

  // deleteTask
  deleteTask(taskId: string): Observable<any> {
    const headers = this.createHeaders();
    if(headers.keys.length === 0) {
      return of(null);
    // 'HttpHeaders.keys' is an Iterator object of headers. 'length === 0' means no headers so no authenticated user.
    }
    return this.httpClient.delete<any>(`http://localhost:3001/tasks/${taskId}`, {headers});
  }

}
