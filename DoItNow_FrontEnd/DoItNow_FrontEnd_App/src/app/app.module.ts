import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// - - - - - - - - - - import - - - - - - - - - -
import { Route, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { UsersComponent } from './components/users/users.component';
import { TasksComponent } from './components/tasks/tasks.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AuthGuard } from './auth/auth.guard';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { TaskCreateComponent } from './components/task-create/task-create.component';
import { TaskDetailsComponent } from './components/task-details/task-details.component';
import { TaskUpdateComponent } from './components/task-update/task-update.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';

// - - - - - - - - - - routes configuration - - - - - - - - - -
const routes: Route[] = [
  { path: '', component: HomeComponent },

  { path: 'tasks', component: TasksComponent, canActivate: [AuthGuard] },
  { path: 'createTask', component: TaskCreateComponent, canActivate: [AuthGuard] },
  { path: 'tasks/:id', component: TaskDetailsComponent, canActivate: [AuthGuard] },
  { path: 'tasks/updateTask/:id', component: TaskUpdateComponent, canActivate: [AuthGuard] },

  { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'users/:id', component: UserDetailsComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  { path: '**', redirectTo: '' }
]

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    NavbarComponent,
    FooterComponent,
    HomeComponent,
    UsersComponent,
    TasksComponent,
    ProfileComponent,
    TaskCreateComponent,
    TaskDetailsComponent,
    TaskUpdateComponent,
    UserDetailsComponent
  ],
  imports: [
    BrowserModule,

    // - - - - - - - - - - imports - - - - - - - - - -
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
