import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { TasksService } from 'src/app/services/tasks.service';
import { Task } from 'src/app/models/task.interface';
import { ActivatedRoute, Router } from '@angular/router'; // ActivatedRoute to get task id from url

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

  constructor(private authSrv: AuthService, private tasksSrv: TasksService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });

    // ActivatedRoute to get task id from url
    this.route.params.subscribe((_params) => {
      const taskId = _params['id'];
      this.loadTaskDetails(taskId);
    });
  }

  // loadTaskDetails
  loadTaskDetails(taskId: string): void {
    this.loadTaskDetailsSub = this.tasksSrv.getTaskDetails(taskId).subscribe((_taskDetails: Task) => {
      this.taskDetails = _taskDetails;
    });
  }

  // updateTask
  updateTask(taskId: string): void {
    this.router.navigate(['/tasks/updateTask', taskId]);
  }

  // goToTasks
  goToTasks(): void {
    this.router.navigate(['/tasks']);
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
