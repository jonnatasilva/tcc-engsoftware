import { ProductsResponse } from 'src/app/products/response/products-response';

export interface StocksResponse {
    stockId: Number;
    product: ProductsResponse;
    amount: number;
}
