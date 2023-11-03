import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { Task } from 'src/app/models/task.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { TasksService } from 'src/app/services/tasks.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit, OnDestroy { // add OnDestroy

  // - - - - - - - - - - TasksComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  tasks: Task[] | undefined;
  tasksSub: Subscription | undefined;

  // tasks pagination
  page: number = 0;
  size: number = 10;
  sortBy: string = 'taskId';
  totalPages: number = 0;
  totalPagesArray: number[] = [];
  pageIndex: number = 0;
  pageFirstRow: number = 1;

  constructor(private authSrv: AuthService, private tasksSrv: TasksService, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });
    this.loadTasks(); // loadTasks with pagination
  }

  // loadTasks with pagination
  loadTasks(): void {
    this.tasksSub = this.tasksSrv.getTasks(this.page, this.size, this.sortBy).subscribe((pageData: any) => {
      this.tasks = pageData.content;
      this.totalPages = pageData.totalPages;
      this.totalPagesArray = Array(this.totalPages).map((i) => i);
    });
  }

  prevPage(): void {
    if(this.page > 0) {
      this.page --;
      this.pageIndex = this.page;
      this.pageFirstRow = this.page * this.size + 1;
      this.loadTasks();
    }
  }

  nextPage(): void {
    if(this.page < this.totalPages - 1) {
      this.page ++;
      this.pageIndex = this.page;
      this.pageFirstRow = this.page * this.size + 1;
      this.loadTasks();
    }
  }

  goToPage(_page: number): void {
    if(_page >= 0 && _page < this.totalPages) {
      this.page = _page;
      this.pageIndex = this.page;
      this.pageFirstRow = this.page * this.size + 1;
      this.loadTasks();
    }
  }

  // createTask
  createTask(): void {
    this.router.navigate(['/createTask']);
  }

  // goToTaskDetails
  goToTaskDetails() {

  }

  // updateTask
  updateTask() {

  }

  // completeTask
  completeTask() {

  }

  // deleteTask
  deleteTask() {

  }

  ngOnDestroy(): void {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
    if(this.tasksSub) {
      this.tasksSub.unsubscribe();
    }
  }

}
