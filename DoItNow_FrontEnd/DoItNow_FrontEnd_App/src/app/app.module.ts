import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// - - - - - - - - - - import - - - - - - - - - -
import { Route, RouterModule } from '@angular/router'

import { AppComponent } from './app.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';

// - - - - - - - - - - routes configuration - - - - - - - - - -
const routes: Route[] = [

  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent }


]


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    NavbarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,

    // - - - - - - - - - - imports - - - - - - - - - -
    RouterModule.forRoot(routes)

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
