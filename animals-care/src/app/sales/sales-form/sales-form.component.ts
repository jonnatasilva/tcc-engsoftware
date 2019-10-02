import { Component, OnInit } from '@angular/core';
import { SaleForm } from './sale-form';
import { SalesItem } from './sales-item';
import { SalesItemRequest, SalesRequest } from '../request/sales-request';
import { SalesService } from '../services/sales.service';
import { NavigationUtilService } from 'src/app/system/utils/navigation-util.service';
import { ProductsService } from 'src/app/products/service/products.service';
import { ProductsResponse } from 'src/app/products/response/products-response';

@Component({
  selector: 'app-sales-form',
  templateUrl: './sales-form.component.html',
  styleUrls: ['./sales-form.component.css']
})
export class SalesFormComponent implements OnInit {

  products = new Array<ProductsResponse>();
  saleItems = new Array<SalesItem>();

  saleForm = new SaleForm();

  constructor(private productService: ProductsService
    , private salesService: SalesService
    , private navigationUtilService: NavigationUtilService) { }

  ngOnInit() {
    this.fillProductsCombo();
  }

  add() {
    this.saleItems.push(this.buildSaleItem());
    
    this.removeProductFromListByProductId(this.saleForm.product.value);
    this.saleForm.product.setValue(0);
  }

  finalizeSale() {
    const salesItems = this.saleItems
    .map(si => {
      return new SalesItemRequest(si.productId, si.amount);
    });
    const salesRequest = new SalesRequest(salesItems);
    this.salesService.createSale(salesRequest)
    .subscribe(sale => {
      this.navigationUtilService.navigateToSales();
    }, err => {
      if (err.status == 400) {
        console.log(alert(err.error['message']));
      } else {
        alert("Sorry! We can not execute your request");        
      }
 
    });
  }

  removeSaleItem(saleItem: SalesItem) {
    this.saleItems = this.saleItems
    .filter(si => si.productId != saleItem.productId);

    this.fillProductsCombo();
  }

  private findProductByProductId(productId: Number): ProductsResponse {
    return this.products
    .find(p => p.productId == productId);
  }

  private buildSaleItem(): SalesItem {
    const product = this.findProductByProductId(this.saleForm.product.value);
    return new SalesItem(product.productId, product.name, this.saleForm.amount.value);
  }

  private removeProductFromListByProductId(productId: Number) {
    this.products = this.products
    .filter(p => p.productId != productId);
  }

  private fillProductsCombo() {
    this.productService.findAllProducts().subscribe(res => {
      this.products = res;
    });
  }
}
