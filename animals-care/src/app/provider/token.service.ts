import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

const TOKEN_NAME = 'access_token';
const EXPIRES = 0.006944444;

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  //eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjQwMjI4MzYsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNzYzNTZmYTAtYWNkOC00NTc0LWJmNTctYWVlODY2NjliOGJhIiwiY2xpZW50X2lkIjoiY2xpZW50SWRQYXNzd29yZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsImRlbGV0ZSJdfQ.AzdYD8fJfD5Cbvlyn4oyA6wKIwdYX2Dfqr6RokSzAK_gbM-ksVDUQmvuTC7vmnJdbA5m9u4VQ4-j8pN__-VuFE3AeSw3NKgjr1JOrjKqKHEZ9XcM16ZjC1DsQxKTIUaJXcgZV-b1KtM3F8i7p5sd792iQ6vf0YMfBr56aEcPZmkaF4qqGsB4V0Ry-xKwPFz92YLZHtFnhwmOaRcFJwITW61BW0BjPo9mH3hGKLsRKBm-JQ8cwSJeMvhyeP7a7lluMM2pIs-6_By733_1asw4GiWlNtgeVxRcIXW-yRjbjU-U2AMmPywNkyEEn3xvcPlhNn_wt_3AaYTpA8DMMJyAQA
  constructor(private cookieService: CookieService) { }

  saveToken(token: string) {
    this.cookieService.set(TOKEN_NAME, token, EXPIRES);
  }

  getToken() {
    return this.cookieService.get(TOKEN_NAME);
  }

  isTokenNotSet() {
    return !this.cookieService.check(TOKEN_NAME);
  }

  deleteToken() {
    return this.cookieService.delete(TOKEN_NAME);
  }
}
