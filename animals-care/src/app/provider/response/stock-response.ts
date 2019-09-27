import { ProductResponse } from './product-response';

export interface StockResponse {

    stockId: Number;
    product: ProductResponse;
    amount: number;
}
