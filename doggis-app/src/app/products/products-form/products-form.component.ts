import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductForm } from './product-form';
import { NavigationUtilService } from 'src/app/system/utils/navigation-util.service';
import { ProductsService } from '../service/products.service';
import { ProductsRequest } from '../request/products--request';

@Component({
  selector: 'app-products-form',
  templateUrl: './products-form.component.html',
  styleUrls: ['./products-form.component.css']
})
export class ProductsFormComponent implements OnInit {

  productForm = new ProductForm();

  manufacturers = [{manufacturerId: 1, name: 'Teste'}];

  constructor(private activatedRouter: ActivatedRoute 
    , private productService: ProductsService
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

  private buildProductRequest(): ProductsRequest {
    const productRequest = new ProductsRequest();
    productRequest.name = this.productForm.name.value;
    productRequest.manufacturerId = this.productForm.manufacturer.value;
    productRequest.manufacturerName = 'Teste';
    productRequest.specifications = this.productForm.specifications.value;

    return productRequest;
  }
}
