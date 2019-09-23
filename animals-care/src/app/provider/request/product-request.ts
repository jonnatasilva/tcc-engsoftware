import { ManufacturerRequest } from './manufacturer-request';

export class ProductRequest {
    name: String;
    manufacturer: ManufacturerRequest;
    specifications: String;
}
