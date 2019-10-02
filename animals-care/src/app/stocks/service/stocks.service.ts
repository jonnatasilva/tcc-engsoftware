import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StocksResponse } from '../response/stocks-response';
import { StocksRequest } from '../request/stocks-request';
import { StocksUpdateRequest } from '../request/stocks-update-request';

@Injectable({
  providedIn: 'root'
})
export class StocksService {

  constructor(private httpClient: HttpClient) { }

  findAllStocks(): Observable<Array<StocksResponse>> {
    return this.httpClient.get<Array<StocksResponse>>('http://localhost:8080/api/v1/stocks');
  }

  findStockById(stockId: Number): Observable<StocksResponse> {
    return this.httpClient.get<StocksResponse>('http://localhost:8080/api/v1/stocks/' + stockId);
  }

  public createStock(stockRequest: StocksRequest): Observable<StocksResponse> {
    return this.httpClient.post<StocksResponse>('http://localhost:8080/api/v1/stocks', stockRequest);
  }

  public updateStockAmount(stockId: number, stockUpdateRequest: StocksUpdateRequest): Observable<StocksResponse> {
    return this.httpClient.patch<StocksResponse>('http://localhost:8080/api/v1/stocks/' + stockId, stockUpdateRequest);
  }
}
