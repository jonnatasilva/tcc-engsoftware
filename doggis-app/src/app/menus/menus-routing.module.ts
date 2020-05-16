import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MenusComponent } from './menus.component';

const routes: Routes = [
  {
     path: '',
     component: MenusComponent,
     children: [
      {
        path: 'products',
        loadChildren: () => import('.././products/products.module').then(m => m.ProductsModule)
      },
      {
        path: 'sales',
        loadChildren: () => import('.././sales/sales.module').then(m => m.SalesModule)
      },
      {
        path: 'stocks'
        , loadChildren: () => import('.././stocks/stocks.module').then(m => m.StocksModule)
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenusRoutingModule { }
