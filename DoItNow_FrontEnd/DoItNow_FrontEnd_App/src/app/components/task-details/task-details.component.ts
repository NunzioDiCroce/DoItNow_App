import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { TasksService } from 'src/app/services/tasks.service';
import { Task } from 'src/app/models/task.interface';

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.scss']
})
export class TaskDetailsComponent implements OnInit, OnDestroy {

  // - - - - - - - - - - TaskDetailsComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  loadTaskDetailsSub: Subscription | undefined;
  taskDetails: Task | undefined;

  constructor(private authSrv: AuthService, private tasksSrv: TasksService) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });
  }

  // loadTaskDetails
  loadTaskDetails(taskId: string): void {
    this.loadTaskDetailsSub = this.tasksSrv.getTaskDetails(taskId).subscribe((_taskDetails: Task) => {
      this.taskDetails = _taskDetails;
    })
  }

  ngOnDestroy(): void {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
    if(this.loadTaskDetailsSub) {
      this.loadTaskDetailsSub.unsubscribe();
    }
  }

}
