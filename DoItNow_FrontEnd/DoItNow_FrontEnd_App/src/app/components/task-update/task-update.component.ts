import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { NgForm } from '@angular/forms';
import { TasksService } from 'src/app/services/tasks.service';
import { Task } from 'src/app/models/task.interface';
import { TaskUpdate } from 'src/app/models/task-update.interface';
import { ActivatedRoute, Router } from '@angular/router'; // ActivatedRoute to get task id from url

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.scss']
})
export class TaskUpdateComponent implements OnInit, OnDestroy {

  // - - - - - - - - - - TaskUpdateComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  loadTaskDetailsSub: Subscription | undefined;
  //taskDetails: TaskUpdate | undefined;
  updateTaskSub: Subscription | undefined;

  userId: string = '';
  taskId: string = '';

  // task initialization
  taskDetails: TaskUpdate = {
    title: '',
    description: '',
    category: '',
    expirationDate: new Date(),
    completed: false,
    notes: ''
  }

  constructor(private authSrv: AuthService, private tasksSrv: TasksService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user
    });
    if(this.user) { // get user.id
      this.userId = this.user.user.id;
    }
    // ActivatedRoute to get task id from url
    this.route.params.subscribe((_params) => {
      this.taskId = _params['id'];
      this.loadTaskDetails(this.taskId);
    });
  }

  // loadTaskDetails
  loadTaskDetails(taskId: string): void {
    this.loadTaskDetailsSub = this.tasksSrv.getTaskDetails(taskId).subscribe((_taskDetails) => {
      this.taskDetails = _taskDetails;
    });
  }

  // updateTask
  updateTask(form: NgForm): void {
    this.updateTaskSub = this.tasksSrv.updateTask(this.userId, this.taskId, this.taskDetails).subscribe(() => {
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
    if(this.loadTaskDetailsSub) {
      this.loadTaskDetailsSub.unsubscribe();
    }
    if(this.updateTaskSub) {
      this.updateTaskSub.unsubscribe();
    }
  }

}
