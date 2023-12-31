import { Injectable } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, map, of } from 'rxjs';
import { Router } from '@angular/router';
import { TaskCreate } from '../models/task-create.interface';
import { TaskUpdate } from '../models/task-update.interface';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  // - - - - - - - - - - TasksService definition - - - - - - - - - -
  constructor(private http: HttpClient, private router: Router) { }

  // getTasks
  getTasks(page: number, size: number, sort: string): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null); // to return an empty observable
    } else {
      const user = JSON.parse(userString);
      const params = new HttpParams().set('page', page.toString()).set('size', size.toString()).set('sort', sort);
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.get<any>('http://localhost:3001/tasks', {params, headers}).pipe(map(response => {
        return {content: response.content, totalElements: response.totalElements, totalPages: Math.ceil(response.totalElements/size)}
      }));
    }
  }

  // getUserTasks
  getUserTasks(page: number, size: number, sort: string): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null); // to return an empty observable
    } else {
      const user = JSON.parse(userString);
      const params = new HttpParams().set('page', page.toString()).set('size', size.toString()).set('sort', sort);
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.get<any>('http://localhost:3001/tasks/usertasks', {params, headers}).pipe(map(response => {
        return {content: response.content, totalElements: response.totalElements, totalPages: Math.ceil(response.totalElements/size)}
      }));
    }
  }

  // createTask
  createTask(userId: string, task: TaskCreate): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null); // to return an empty observable
    } else {
      const user = JSON.parse(userString);
      const params = new HttpParams().set('userId', userId.toString());
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.post<any>('http://localhost:3001/tasks', task, {params, headers});
    }
  }

  // getTaskDetails
  getTaskDetails(taskId: string): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null); // to return an empty observable
    } else {
      const user = JSON.parse(userString);
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.get<any>(`http://localhost:3001/tasks/${taskId}`, {headers});
    }
  }

  // updateTask
  updateTask(userId: string, taskId: string, task: TaskUpdate): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null);
    } else {
      const user = JSON.parse(userString);
      const params = new HttpParams().set('userId', userId.toString());
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.put<any>(`http://localhost:3001/tasks/${taskId}`, task, {params, headers});
    }
  }

  // completeTask
  completeTask(userId: string, taskId: string, taskToComplete: TaskUpdate): Observable<any>{
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null);
    } else {
      const user = JSON.parse(userString);
      const params = new HttpParams().set('userId', userId.toString());
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.put<any>(`http://localhost:3001/tasks/${taskId}`, taskToComplete, {params, headers});
    }
  }

  // deleteTask
  deleteTask(taskId: string): Observable<any> {
    const userString = localStorage.getItem('user');
    if(!userString) {
      this.router.navigate(['/login']);
      return of(null);
    } else {
      const user = JSON.parse(userString);
      const token = user.accessToken;
      const headers = new HttpHeaders({Authorization: `Bearer ${token}`});
      return this.http.delete<any>(`http://localhost:3001/tasks/${taskId}`, {headers});
    }
  }

}
