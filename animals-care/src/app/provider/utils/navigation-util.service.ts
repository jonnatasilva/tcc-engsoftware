import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavigationUtilService {

  constructor(private router: Router) { }

  navigateToLogin() {
    this.router.navigateByUrl('');
  }

  navigateToProducts() {
    this.router.navigateByUrl('/products');
  }
}
