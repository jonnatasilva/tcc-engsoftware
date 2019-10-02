import { ManufacturersResponse } from './manufacturers-response';

export interface ProductsResponse {
    productId: Number;
    name: String;
    specifications: String;
    manufacturer: ManufacturersResponse;
    createdAt: Date;
    updatedAt: Date;
}
