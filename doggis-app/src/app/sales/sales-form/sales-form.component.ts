import { Component, OnInit } from '@angular/core';
import { ProductsResponse } from 'src/app/products/response/products-response';
import { ProductsService } from 'src/app/products/service/products.service';
import { MessageService } from 'src/app/system/message.service';
import { SalesItemRequest, SalesRequest } from '../request/sales-request';
import { SalesService } from '../services/sales.service';
import { SaleForm } from './sale-form';
import { SalesItem } from './sales-item';

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
    , private messageSevice: MessageService) { }

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
    
    this.salesService.createSale(new SalesRequest(salesItems))
    .subscribe(sale => {
      this.saleItems = [];
      this.saleForm.reset();
      this.messageSevice.showMessage('Venda efetuada com sucesso!');
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
    return new SalesItem(product.productId, product.name, product.price, this.saleForm.amount.value);
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

  get saleAmount(): number {
    if (this.saleItems.length > 0) {
      return this.saleItems
      .map(item => item.productPrice * item.amount)
      .reduce((x, y) => x + y);
    }
    return 0;
  }
}
