import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductsResponse } from '../response/products-response';
import { ProductsRequest } from '../request/products--request';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private httpClient: HttpClient) { }

  public findAllProducts(): Observable<Array<ProductsResponse>> {
    return this.httpClient.get<Array<ProductsResponse>>('http://localhost:8080/api/v1/products');
  }

  public findProductByProductId(productId: Number): Observable<ProductsResponse> {
    return this.httpClient.get<ProductsResponse>('http://localhost:8080/api/v1/products/' + productId);
  }

  public createProduct(productRequest: ProductsRequest) {
    return this.httpClient.post<ProductsResponse>('http://localhost:8080/api/v1/products', productRequest);
  }

  public updateProduct(productId: number, productRequest: ProductsRequest): Observable<ProductsResponse> {
    return this.httpClient.put<ProductsResponse>('http://localhost:8080/api/v1/products/' + productId, productRequest);
  }
}
