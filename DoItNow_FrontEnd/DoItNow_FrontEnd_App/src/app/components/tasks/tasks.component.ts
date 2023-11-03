import { Component, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Task } from 'src/app/models/task.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { TasksService } from 'src/app/services/tasks.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {

  // - - - - - - - - - - TasksComponent definition - - - - - - - - - -
  user!: AuthData | null;
  tasks: Task[] | undefined;

  // tasks pagination
  page: number = 0;
  size: number = 10;
  sortBy: string = 'taskId';
  totalPages: number = 0;
  totalPagesArray: number[] = [];

  constructor(private authSrv: AuthService, private tasksSrv: TasksService) { }

  ngOnInit(): void {
    this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });
    this.loadTasks(); // loadTasks with pagination
  }

  // loadTasks with pagination
  loadTasks(): void {
    this.tasksSrv.getTasks(this.page, this.size, this.sortBy).subscribe((pageData: any) => {
      this.tasks = pageData.content;
      this.totalPages = pageData.totalPages;
      this.totalPagesArray = Array(this.totalPages).map((i) => i);
    });
  }

}
