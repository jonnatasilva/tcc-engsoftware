import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const PRODUCTS_PATH = environment.doggis_api_server  + '/products';

@Injectable({
  providedIn: 'root'
})
export class EndpointProductUtils {

  constructor() { }

  raiz() {
    return PRODUCTS_PATH;
  }

  findByProductId(productId: Number) {
    return `${this.raiz()}/${productId}`;
  }

  updateProduct(productId: Number) {
    return `${this.raiz()}/${productId}`;
  }
}
