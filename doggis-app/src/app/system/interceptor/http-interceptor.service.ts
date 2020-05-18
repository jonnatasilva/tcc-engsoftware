import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { TokenService } from '../token.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private tokenService: TokenService,
    private messageService: MessageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req.headers.set('Authorization', 'Bearer ' + this.tokenService.getToken());
    return next.handle(req)
    .pipe(catchError((error: HttpErrorResponse) => {
      if (error.status == 400) {
        this.messageService.(alert(error.error['message']));
      } else {
        alert("Sorry! We can not execute your request");        
      }
      return throwError(error);
    }));
  }
  
}
