import { Component, OnInit } from '@angular/core';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { LoginService } from '../provider/login/login.service';
import { Router } from '@angular/router';
import { finalize} from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { LoadingService } from '../provider/loading/loading.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  isLoading = false;
  feedback: string;

  constructor(private loginService: LoginService
    , private route: Router
    , private loadingService: LoadingService) { }

  ngOnInit() {
    this.loginService.removeDataAccess();
  }

  login() {
    this.loadingService.show();
    this.resetFeedback();
    
    this.loginService
    .login(this.username.value, this.password.value)
    .pipe(
      finalize(() => this.loadingService.hide())
    )
    .subscribe(res => {
      this.loginService.saveDataAccess(res['access_token']);
      this.route.navigateByUrl('home');
    }, err =>  {
      this.feedback = this.handleError(err);
    });
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  private handleError(err: HttpErrorResponse) {
    if (err.status == 400 && err.error.error == 'invalid_grant') {
      return 'Username or Password is inválid';
    } else {
      return 'Fail to login, try it again.'
    }
  }

  private resetFeedback() {
    this.feedback = '';
  }

}
