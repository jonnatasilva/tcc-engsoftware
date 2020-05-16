import { ManufacturersResponse } from './manufacturers-response';

export interface ProductsResponse {
    productId: Number;
    name: String;
    price: Number;
    specifications: String;
    manufacturer: ManufacturersResponse;
    createdAt: Date;
    updatedAt: Date;
}
