import { Component, OnInit } from '@angular/core';
import { StockForm } from './stocks-form';
import { ProductService } from 'src/app/provider/service/product.service';
import { StockService } from 'src/app/provider/service/stock.service';
import { StockRequest, StockProductRequest } from 'src/app/provider/request/stock-request';
import { NavigationUtilService } from 'src/app/provider/utils/navigation-util.service';
import { ActivatedRoute } from '@angular/router';
import { StockUpdateRequest } from 'src/app/provider/request/stock-update-request';

@Component({
  selector: 'app-stock-form',
  templateUrl: './stocks-form.component.html',
  styleUrls: ['./stocks-form.component.css']
})
export class StockFormComponent implements OnInit {

  products = [];

  stockForm = new StockForm();
  actualAmount = this.stockForm.amount.value;

  constructor(private productService: ProductService
    , private stockService: StockService
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

  private buildStockRequest(): StockRequest {
    const stockRequest = new StockRequest();
    stockRequest.product = new StockProductRequest();
    stockRequest.product.productId = this.stockForm.product.value;
    stockRequest.amount = this.stockForm.amount.value;

    return stockRequest;
  }

  private buildStockUpdateRequest(): StockUpdateRequest {
    const amountToAdd =  Number(this.stockForm.amount.value) - this.actualAmount;
    const stockUpdateRequest = new StockUpdateRequest(amountToAdd);

    return stockUpdateRequest;
  }

}
