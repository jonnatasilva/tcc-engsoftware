import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SalesRequest } from '../request/sales-request';
import { Observable } from 'rxjs';
import { SalesResponse } from '../response/sales-response';

@Injectable({
  providedIn: 'root'
})
export class SalesService {

  constructor(private httpClient: HttpClient) { }

  public createSale(saleRequest: SalesRequest): Observable<SalesResponse> {
    return this.httpClient.post<SalesResponse>('http://localhost:8080/api/v1/sales', saleRequest);
  }
}
