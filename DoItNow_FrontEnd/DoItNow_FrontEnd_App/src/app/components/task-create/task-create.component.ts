import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { NgForm } from '@angular/forms';
import { TasksService } from 'src/app/services/tasks.service';
import { TaskCreate } from 'src/app/models/task-create.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.scss']
})
export class TaskCreateComponent implements OnInit, OnDestroy { // add OnDestroy

  // - - - - - - - - - - TaskCreateComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  createTaskSub: Subscription | undefined;

  userId: string = '';

  // task initialization
  task: TaskCreate = {
    title: '',
    description: '',
    category: '',
    expirationDate: new Date(),
    notes: ''
  };

  constructor(private authSrv: AuthService, private tasksSrv: TasksService, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });
    if(this.user) { // get user.id
      this.userId = this.user.user.id;
    }
  }

  // createTask
  createTask(form: NgForm): void {
    this.createTaskSub = this.tasksSrv.createTask(this.userId, this.task).subscribe(() => {
      window.alert('Task creation success!');
      this.router.navigate(['/tasks']);
    });
  }

  // cancelCreation
  cancelCreation(): void {
    const confirmation = window.confirm('Are you sure you want to cancel the current operation?');
    if(confirmation) {
      this.router.navigate(['/tasks']);
    }
  }

  ngOnDestroy(): void {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
    if(this.createTaskSub) {
      this.createTaskSub.unsubscribe();
    }
  }

}
