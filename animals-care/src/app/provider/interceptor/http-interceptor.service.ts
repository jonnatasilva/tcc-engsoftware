import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../token.service';
import { Router } from '@angular/router';
import { NavigationUtilService } from '../utils/navigation-util.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private tokenService: TokenService
    , private navigationUtils: NavigationUtilService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.tokenService.isTokenNotSet()) {
      this.navigationUtils.navigateToLogin();
    }

    req.headers.set('Authorization', 'Bearer ' + this.tokenService.getToken());
    return next.handle(req);
  }
}
