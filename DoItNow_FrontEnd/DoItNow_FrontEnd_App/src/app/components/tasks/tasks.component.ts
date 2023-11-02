import { Component, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {

  // - - - - - - - - - - TasksComponent definition - - - - - - - - - -
  user!: AuthData | null;

  constructor(private authSrv: AuthService) { }

  ngOnInit(): void {
  }

}
