export class ProductsRequest {
    name: String;
    price: number;
    manufacturer = new ManufacturersRequest();
    specifications: String;

    set manufacturerId(manufacturerId: Number) {
        this.manufacturer.manufacturerId = manufacturerId;
    }

    set manufacturerName(manufacturerName: String) {
        this.manufacturer.name = manufacturerName;
    }
}

class ManufacturersRequest {
    manufacturerId: Number;
    name: String;
}
