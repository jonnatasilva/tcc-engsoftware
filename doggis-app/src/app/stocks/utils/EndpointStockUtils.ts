import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const STOCK_PATH = `${environment.doggis_api_server}/stocks`;

@Injectable({
  providedIn: 'root'
})
export class EndpointStockUtils {

  constructor() { }

  raiz() {
    return STOCK_PATH;
  }

  findStockById(stockId: Number) {
    return `${this.raiz()}/${stockId}`;
  }

  updateStock(stockId: Number) {
    return `${this.raiz()}/${stockId}`;
  }
}
