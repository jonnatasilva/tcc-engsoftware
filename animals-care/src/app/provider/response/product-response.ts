import { ManufacturerResponse } from './manufacturer-response';

export interface ProductResponse {
    productId: Number;
    name: String;
    specifications: String;
    manufacturer: ManufacturerResponse;
    createdAt: Date;
    updatedAt: Date;
}
