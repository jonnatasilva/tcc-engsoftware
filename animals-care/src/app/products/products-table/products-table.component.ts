import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ProductsService } from '../service/products.service';

@Component({
  selector: 'app-products-table',
  templateUrl: './products-table.component.html',
  styleUrls: ['./products-table.component.css']
})
export class ProductsTableComponent implements OnInit {
  displayedColumns: string[] = ['productId', 'name', 'price', 'manufacturer', 'specifications', 'createdAt', 'updatedAt' ];

  @Input()
  dataSource = new MatTableDataSource();

  @ViewChild(MatSort, {static: true}) sort: MatSort;

  productsList = new MatTableDataSource();

  constructor(private productService: ProductsService) { }

  ngOnInit() {
    this.dataSource.sort = this.sort;
    this.loadProducts();
  }

  private loadProducts() {
    this.productService.findAllProducts()
    .subscribe(res => {
      this.productsList.data = res;
    });
  }

}
