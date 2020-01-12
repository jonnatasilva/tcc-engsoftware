import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StocksResponse } from '../response/stocks-response';
import { StocksRequest } from '../request/stocks-request';
import { StocksUpdateRequest } from '../request/stocks-update-request';
import { EndpointStockUtils } from '../utils/EndpointStockUtils';

@Injectable({
  providedIn: 'root'
})
export class StocksService {

  constructor(private httpClient: HttpClient
    , private endpointStockUtils: EndpointStockUtils) { }

  findAllStocks(): Observable<Array<StocksResponse>> {
    return this.httpClient.get<Array<StocksResponse>>(this.endpointStockUtils.raiz());
  }

  findStockById(stockId: Number): Observable<StocksResponse> {
    return this.httpClient.get<StocksResponse>(this.endpointStockUtils.findStockById(stockId));
  }

  public createStock(stockRequest: StocksRequest): Observable<StocksResponse> {
    return this.httpClient.post<StocksResponse>(this.endpointStockUtils.raiz(), stockRequest);
  }

  public updateStockAmount(stockId: number, stockUpdateRequest: StocksUpdateRequest): Observable<StocksResponse> {
    return this.httpClient.patch<StocksResponse>(this.endpointStockUtils.updateStock(stockId), stockUpdateRequest);
  }
}
