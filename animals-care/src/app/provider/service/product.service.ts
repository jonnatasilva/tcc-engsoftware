import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse } from '../response/product-response';
import { ProductRequest } from '../request/product-request';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  public findAllProducts(): Observable<Array<ProductResponse>> {
    return this.httpClient.get<Array<ProductResponse>>('http://localhost:8080/api/v1/products');
  }

  public findProductByProductId(productId: number): Observable<ProductResponse> {
    return this.httpClient.get<ProductResponse>('http://localhost:8080/api/v1/products/' + productId);
  }

  public createProduct(productRequest: ProductRequest) {
    return this.httpClient.post<ProductResponse>('http://localhost:8080/api/v1/products', productRequest);
  }

  public updateProduct(productId: number, productRequest: ProductRequest): Observable<ProductResponse> {
    return this.httpClient.put<ProductResponse>('http://localhost:8080/api/v1/products/' + productId, productRequest);
  }
}
