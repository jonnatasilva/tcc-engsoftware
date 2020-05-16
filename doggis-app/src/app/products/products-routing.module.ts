import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductsTableComponent } from './products-table/products-table.component';
import { ProductsFormComponent } from './products-form/products-form.component';


const routes: Routes = [
  {
    path: '',
    component: ProductsTableComponent
  },
  {
    path: 'new',
    component: ProductsFormComponent
  },
  {
    path: ':pathProductId',
    component: ProductsFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
