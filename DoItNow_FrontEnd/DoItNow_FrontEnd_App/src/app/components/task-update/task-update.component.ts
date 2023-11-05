import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { NgForm } from '@angular/forms';
import { TasksService } from 'src/app/services/tasks.service';
import { TaskUpdate } from 'src/app/models/task-update.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.scss']
})
export class TaskUpdateComponent implements OnInit, OnDestroy {

  // - - - - - - - - - - TaskUpdateComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  getTaskDetailsSub: Subscription | undefined;
  updateTaskSub: Subscription | undefined;

  userId: string = '';
  taskId: string = '';

  // task initialization
  task: TaskUpdate = {
    title: '',
    description: '',
    category: '',
    expirationDate: new Date(),
    completed: false,
    notes: ''
  }

  constructor(private authSrv: AuthService, private tasksSrv: TasksService, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user
    });
    if(this.user) { // get user.id
      this.userId = this.user?.user.id;
    }
    // recover existing task data
    this.getTaskDetailsSub = this.tasksSrv.getTaskDetails(this.taskId).subscribe((_taskDetails) => {
      this.task = _taskDetails
    });
  }

  // updateTask
  updateTask(form: NgForm): void {
    this.updateTaskSub = this.tasksSrv.updateTask(this.userId, this.taskId, this.task).subscribe(() => {
      window.alert('Task update success!')
      this.router.navigate(['/tasks']);
    });
  }

  // cancelUpdate
  cancelUpdate(): void {
    const confirmation = window.confirm('Are you sure you want to cancel the current operation?');
    if(confirmation) {
      this.router.navigate(['/tasks']);
    }
  }

  ngOnDestroy(): void {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
    if(this.getTaskDetailsSub) {
      this.getTaskDetailsSub.unsubscribe();
    }
  }

}
