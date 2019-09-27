import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/provider/service/product.service';
import { ProductForm } from './product-form';
import { ProductRequest } from 'src/app/provider/request/product-request';
import { ManufacturerRequest } from 'src/app/provider/request/manufacturer-request';
import { NavigationUtilService } from 'src/app/provider/utils/navigation-util.service';

@Component({
  selector: 'app-products-form',
  templateUrl: './products-form.component.html',
  styleUrls: ['./products-form.component.css']
})
export class ProductsFormComponent implements OnInit {

  productForm = new ProductForm();

  manufacturers = [{manufacturerId: 1, name: 'Teste'}];

  constructor(private activatedRouter: ActivatedRoute 
    , private productService: ProductService
    , private navigationUtilService: NavigationUtilService) { }

  ngOnInit() {
    this.activatedRouter.params.subscribe(params => {
      if (params['pathProductId']) {
        this.productService.findProductByProductId(params['pathProductId'])
        .subscribe(res => {
          this.productForm.setFromProductResponse(res);
        })
      }
    });
  }

  add() {
    if (this.productForm.productId.value > 0) {
      this.productService.updateProduct(this.productForm.productId.value, this.buildProductRequest())
      .subscribe(res => {
        this.navigationUtilService.navigateToProducts();
      });
    } else {
      this.productService.createProduct(this.buildProductRequest())
      .subscribe(res => {
        this.navigationUtilService.navigateToProducts();
      });
    }
  }

  private buildProductRequest(): ProductRequest {
    const productRequest = new ProductRequest();
    productRequest.name = this.productForm.name.value;
    productRequest.manufacturer = new ManufacturerRequest(this.productForm.manufacturer.value, 'Teste');
    productRequest.specifications = this.productForm.specifications.value;

    return productRequest;
  }
}
