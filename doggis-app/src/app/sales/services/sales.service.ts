import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SalesRequest } from '../request/sales-request';
import { Observable } from 'rxjs';
import { SalesResponse } from '../response/sales-response';
import { EndpointSaleUtils } from '../utils/EndpointSaleUtils';

@Injectable({
  providedIn: 'root'
})
export class SalesService {

  constructor(private httpClient: HttpClient
    , private endpointSaleUtils: EndpointSaleUtils) { }

  public createSale(saleRequest: SalesRequest): Observable<SalesResponse> {
    return this.httpClient.post<SalesResponse>(this.endpointSaleUtils.raiz(), saleRequest);
  }
}
