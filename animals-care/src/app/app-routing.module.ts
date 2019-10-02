import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { 
    path: 'login',
    loadChildren: () => import('./logins/logins.module').then(m => m.LoginsModule)
  },
  { path: 'logout', redirectTo: 'login', pathMatch: 'full' },
  { path: 'app', redirectTo: '/menu' },
  {
    path: 'menu',
    loadChildren: () => import('./menus/menus.module').then(m => m.MenusModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
