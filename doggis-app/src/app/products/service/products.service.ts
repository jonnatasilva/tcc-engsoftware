import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductsResponse } from '../response/products-response';
import { ProductsRequest } from '../request/products--request';
import { EndpointProductUtils } from '../utils/EnpointProductUtils';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private httpClient: HttpClient
    , private endpointProductUtils: EndpointProductUtils) { }

  public findAllProducts(): Observable<Array<ProductsResponse>> {
    return this.httpClient.get<Array<ProductsResponse>>(this.endpointProductUtils.raiz());
  }

  public findProductByProductId(productId: Number): Observable<ProductsResponse> {
    return this.httpClient.get<ProductsResponse>(this.endpointProductUtils.findByProductId(productId));
  }

  public createProduct(productRequest: ProductsRequest) {
    return this.httpClient.post<ProductsResponse>(this.endpointProductUtils.raiz(), productRequest);
  }

  public updateProduct(productId: number, productRequest: ProductsRequest): Observable<ProductsResponse> {
    return this.httpClient.put<ProductsResponse>(this.endpointProductUtils.updateProduct(productId), productRequest);
  }
}
