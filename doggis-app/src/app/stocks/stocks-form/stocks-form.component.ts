import { Component, OnInit } from '@angular/core';
import { StockForm } from './stocks-form';

import { NavigationUtilService } from 'src/app/system/utils/navigation-util.service';
import { ActivatedRoute } from '@angular/router';
import { ProductsService } from 'src/app/products/service/products.service';
import { StocksService } from '../service/stocks.service';
import { StocksRequest } from '../request/stocks-request';
import { StocksUpdateRequest } from '../request/stocks-update-request';

@Component({
  selector: 'app-stock-form',
  templateUrl: './stocks-form.component.html',
  styleUrls: ['./stocks-form.component.css']
})
export class StockFormComponent implements OnInit {

  products = [];

  stockForm = new StockForm();
  actualAmount = this.stockForm.amount.value;

  constructor(private productService: ProductsService
    , private stockService: StocksService
    , private navigationUtilService: NavigationUtilService
    , private activatedRouter: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRouter.params.subscribe(params => {
      if (params['pathStockId']) {
        this.prepareEdition(params['pathStockId']);
      } else {
        this.productService.findAllProducts().subscribe(res => {
          this.products = res;
        });
      }
    });
    
  }

  private prepareEdition(stockId: Number) {
    this.stockService.findStockById(stockId)
    .subscribe(stock => {
      this.actualAmount = stock.amount;
      this.productService.findProductByProductId(stock.product.productId)
      .subscribe(product => {
        this.products = [product];
        this.stockForm.product.disable();
        this.stockForm.setFromStockResponse(stock);
      });
    });
  }

  add() {
    if (this.stockForm.stockId.value > 0) {
      this.updateStockAmount();
    } else {
      this.stockService.createStock(this.buildStockRequest()).subscribe(res => {
        this.navigationUtilService.navigateToStocks();
      });
    }
  }

  private updateStockAmount() { 
    this.stockService.updateStockAmount(this.stockForm.stockId.value
      , this.buildStockUpdateRequest())
    .subscribe(res => {
      this.navigationUtilService.navigateToStocks();
    });
  }

  private buildStockRequest(): StocksRequest {
    const stockRequest = new StocksRequest();
    stockRequest.productId = this.stockForm.product.value;
    stockRequest.amount = this.stockForm.amount.value;

    return stockRequest;
  }

  private buildStockUpdateRequest(): StocksUpdateRequest {
    const amountToAdd =  Number(this.stockForm.amount.value) - this.actualAmount;
    const stockUpdateRequest = new StocksUpdateRequest(amountToAdd);

    return stockUpdateRequest;
  }

}
