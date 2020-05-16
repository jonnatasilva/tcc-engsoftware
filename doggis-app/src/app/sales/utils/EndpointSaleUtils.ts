import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const SALE_PATH = `${environment.doggis_api_server}/sales`;

@Injectable({
  providedIn: 'root'
})
export class EndpointSaleUtils {

  constructor() { }

  raiz() {
    return SALE_PATH;
  }
}
