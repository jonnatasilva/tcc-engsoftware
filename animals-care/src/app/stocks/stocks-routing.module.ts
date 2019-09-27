import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StockFormComponent } from './stocks-form/stocks-form.component';
import { StockTableComponent } from './stocks-table/stocks-table.component';

const routes: Routes = [
  {
    path: '',
    component: StockTableComponent
  },
  {
    path: 'new',
    component: StockFormComponent,
  },
  {
    path: ':pathStockId',
    component: StockFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StocksRoutingModule { }
