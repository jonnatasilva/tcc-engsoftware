import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Validators, FormGroup, FormControl } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginsService } from './service/logins.service';
import { LoadingsService } from '../loadings/service/loadings.service';

@Component({
  selector: 'app-logins',
  templateUrl: './logins.component.html',
  styleUrls: ['./logins.component.css']
})
export class LoginsComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  isLoading = false;
  feedback: string;

  constructor(private loginService: LoginsService
    , private route: Router
    , private loadingService: LoadingsService) { }

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
      this.route.navigateByUrl('menu');
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
