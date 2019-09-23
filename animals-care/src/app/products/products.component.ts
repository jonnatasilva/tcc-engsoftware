import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductService } from '../provider/service/product.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  productsList = new MatTableDataSource();

  constructor(private productService: ProductService) { }
  
  ngOnInit() {
    this.loadProducts();
  }

  private loadProducts() {
    this.productService.findAllProducts()
    .subscribe(res => {
      this.productsList.data = res;
    });
  }
}
