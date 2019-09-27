import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StockResponse } from '../response/stock-response';
import { HttpClient } from '@angular/common/http';
import { StockRequest } from '../request/stock-request';
import { StockUpdateRequest } from '../request/stock-update-request';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private httpClient: HttpClient) { }

  findAllStocks(): Observable<Array<StockResponse>> {
    return this.httpClient.get<Array<StockResponse>>('http://localhost:8080/api/v1/stocks');
  }

  findStockById(stockId: Number): Observable<StockResponse> {
    return this.httpClient.get<StockResponse>('http://localhost:8080/api/v1/stocks/' + stockId);
  }

  public createStock(stockRequest: StockRequest): Observable<StockResponse> {
    return this.httpClient.post<StockResponse>('http://localhost:8080/api/v1/stocks', stockRequest);
  }

  public updateStockAmount(stockId: number, stockUpdateRequest: StockUpdateRequest): Observable<StockResponse> {
    return this.httpClient.patch<StockResponse>('http://localhost:8080/api/v1/stocks/' + stockId, stockUpdateRequest);
  }
}
