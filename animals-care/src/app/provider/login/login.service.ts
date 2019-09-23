import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from "../../../environments/environment";
import { Observable } from 'rxjs';
import { TokenService } from '../token.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient
    , private tokenService: TokenService
    , private route: Router) { }

  login(username: string, password: string): Observable<Response> {
    return this.http
    .post<Response>(this.getLoginServiceUrl(username, password), null, { headers:  this.buildHeaders()});
  }

  saveDataAccess(dataAccess: string) {
    this.tokenService.saveToken(dataAccess);
  }

  removeDataAccess() {
    this.tokenService.deleteToken();
  }

  private getLoginServiceUrl(username: string, password: string): string {
    return environment.authentication_server
    + '/token?grant_type=password&username='+ username
    +'&password='+ password
    +'&client_id='+ environment.authentication_server_client_id;
  }

  private buildHeaders(): HttpHeaders {
    return new HttpHeaders({ 'Authorization': this.getAuthorizationHeader() });
  }

  private getAuthorizationHeader(): string {
    return 'Basic ' + btoa(environment.authentication_server_client_id + ':' + environment.authentication_server_client_secret);
  }
}
