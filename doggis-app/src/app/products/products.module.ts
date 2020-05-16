import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductsRoutingModule } from './products-routing.module';
import { ProductsComponent } from './products.component';
import { ProductsFormComponent } from './products-form/products-form.component';
import { ProductsTableComponent } from './products-table/products-table.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { ProductsService } from './service/products.service';
import { EndpointProductUtils } from './utils/EnpointProductUtils';


@NgModule({
  declarations: [
    ProductsComponent,
    ProductsFormComponent,
    ProductsTableComponent
  ],
  imports: [
    CommonModule,
    ProductsRoutingModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
    MatSortModule,
    MatTableModule
  ],
  providers: [
    ProductsService,
    EndpointProductUtils
  ]
})
export class ProductsModule { }
