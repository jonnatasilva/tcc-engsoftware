export class ProductsRequest {
    name: String;
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
