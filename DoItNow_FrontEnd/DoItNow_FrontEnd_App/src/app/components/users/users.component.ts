import { Component, OnDestroy, OnInit } from '@angular/core';

// - - - - - - - - - - import - - - - - - - - - -
import { AuthData } from 'src/app/auth/auth-data.interface';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user.interface';
import { AuthService } from 'src/app/auth/auth.service';
import { UsersService } from 'src/app/services/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, OnDestroy {

  // - - - - - - - - - - UsersComponent definition - - - - - - - - - -
  user!: AuthData | null;
  authSub!: Subscription | null;
  users: User[] = [];
  loadUsersSub: Subscription | undefined;

  // tasks pagination
  currentPage = 0;
  currentPageIndex = 0;
  pageSize = 10;
  sortBy = 'taskId';
  totalPages = 0;
  totalPagesArray: number[] = [];
  pageFirstRow: number = 1;

  constructor(private authSrv: AuthService, private usersSrv: UsersService, private router: Router) { }

  ngOnInit(): void {
    this.authSub = this.authSrv.user$.subscribe((_user) => {
      this.user = _user;
    });
    this.loadUsers(); // loadUsers with pagination
  }

  // loadUsers with pagination
  loadUsers(): void {
    this.loadUsersSub = this.usersSrv.getUsers(this.currentPage, this.pageSize, this.sortBy).subscribe((pageData: any) => {
      this.users = pageData.content;
      this.totalPages = pageData.totalPages;
      this.totalPagesArray = Array(this.totalPages).fill(0).map((x, i) => i);
    });
  }

  prevPage(): void {
    if(this.currentPage > 0) {
      this.currentPage --;
      this.currentPageIndex = this.currentPage;
      this.pageFirstRow = this.currentPage * this.pageSize + 1;
      this.loadUsers();
    }
  }

  goToPage(page: number): void {
    if(page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.currentPageIndex = page;
      this.pageFirstRow = this.currentPage * this.pageSize + 1;
      this.loadUsers();
    }
  }

  nextPage(): void {
    if(this.currentPage < this.totalPages - 1) {
      this.currentPage ++;
      this.currentPageIndex = this.currentPage;
      this.pageFirstRow = this.currentPage * this.pageSize + 1;
      this.loadUsers();
    }
  }

  // goToUserDetails
  goToUserDetails(userId: string): void {
    this.router.navigate(['/users', userId]);
  }

  ngOnDestroy() {
    if(this.authSub) {
      this.authSub.unsubscribe();
    }
    if(this.loadUsersSub) {
      this.loadUsersSub.unsubscribe();
    }
  }

}
