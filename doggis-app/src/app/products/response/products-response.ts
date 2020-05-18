import { ManufacturersResponse } from './manufacturers-response';

export interface ProductsResponse {
    productId: Number;
    name: String;
    price: number;
    specifications: String;
    manufacturer: ManufacturersResponse;
    createdAt: Date;
    updatedAt: Date;
}
