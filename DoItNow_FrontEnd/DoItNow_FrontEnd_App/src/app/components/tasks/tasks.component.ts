import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { Task } from 'src/app/models/task.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { TasksService } from 'src/app/services/tasks.service';
import { Router } from '@angular/router';
import { TaskUpdate } from 'src/app/models/task-update.interface';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit, OnDestroy { // add OnDestroy

  // - - - - - - - - - - TasksComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  tasks: Task[] = [];
  loadTasksSub: Subscription | undefined;
  completeTaskSub: Subscription | undefined;
  deleteTasksSub: Subscription | undefined;

  userId: string = '';

  // task initialization
  taskToUpdate: TaskUpdate = {
    title: '',
    description: '',
    category: '',
    expirationDate: new Date(),
    completed: false,
    notes: ''
  }

  // tasks pagination
  currentPage = 0;
  currentPageIndex = 0;
  pageSize = 10;
  sort = 'taskId';
  totalPages = 0;
  totalPagesArray: number[] = [];
  pageFirstRow: number = 1;

  constructor(private authSrv: AuthService, private tasksSrv: TasksService, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });
    if(this.user) {
      this.userId = this.user.user.id;
    }
    this.loadTasks(); // loadTasks with pagination
  }

  // loadTasks with pagination
  loadTasks(): void {
    this.loadTasksSub = this.tasksSrv.getUserTasks(this.currentPage, this.pageSize, this.sort).subscribe((pageData: any) => {
      this.tasks = pageData.content;
      this.totalPages = pageData.totalPages;
      this.totalPagesArray = Array(this.totalPages).fill(0).map((x, i) => i);
    });
  }

  prevPage(): void {
    if(this.currentPage > 0) {
      this.currentPage --;
      this.currentPageIndex = this.currentPage;
      this.pageFirstRow = this.currentPage * this.pageSize + 1;
      this.loadTasks();
    }
  }

  goToPage(page: number): void {
    if(page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.currentPageIndex = page;
      this.pageFirstRow = this.currentPage * this.pageSize + 1;
      this.loadTasks();
    }
  }

  nextPage(): void {
    if(this.currentPage < this.totalPages - 1) {
      this.currentPage ++;
      this.currentPageIndex = this.currentPage;
      this.pageFirstRow = this.currentPage * this.pageSize + 1;
      this.loadTasks();
    }
  }

  // createTask
  createTask(): void {
    this.router.navigate(['/createTask']);
  }

  // goToTaskDetails
  goToTaskDetails(taskId: string): void {
    this.router.navigate(['/tasks', taskId]);
  }

  // completeTask (checkboxChange + completeTasks)
  checkboxChange(event: Event, taskId: string): void {
    const checkbox = event.target as HTMLInputElement;
    const newStatus = checkbox.checked;
    const confirmation = window.confirm('Are you sure you want to complete the task?');
    if(!confirmation) {
      checkbox.checked = !newStatus;
    } else {
      this.completeTask(taskId, newStatus);
    }
  }

  completeTask(taskId: string, newStatus: boolean): void {
    this.tasksSrv.getTaskDetails(taskId).subscribe((_taskDetails) => {
      this.taskToUpdate = _taskDetails
      this.taskToUpdate.completed = newStatus;
      this.completeTaskSub = this.tasksSrv.completeTask(this.userId, taskId, this.taskToUpdate).subscribe(() => {
        this.loadTasks();
      });
    });
  }

  // deleteTask
  deleteTask(taskId: string): void {
    const confirmation = window.confirm('Are you sure you want to delete the task?');
    if(confirmation) {
      this.deleteTasksSub = this.tasksSrv.deleteTask(taskId).subscribe(() => {
        window.alert('Task deleted!');
        this.loadTasks();
      });
    }
  }

  ngOnDestroy(): void {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
    if(this.loadTasksSub) {
      this.loadTasksSub.unsubscribe();
    }
    if(this.completeTaskSub) {
      this.completeTaskSub.unsubscribe();
    }
    if(this.deleteTasksSub) {
      this.deleteTasksSub.unsubscribe();
    }
  }

}
